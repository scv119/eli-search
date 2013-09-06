package com.eli.index.manager;

import com.eli.index.DocumentSupport;
import com.eli.util.Config;
import com.eli.util.TimeUtil;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 6/19/12
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ZhihuIndexManager {
    INSTANCE;
    private static final Logger logger = Logger.getLogger(ZhihuIndexManager.class);

    ZhihuIndex currentIndex;
    ZhihuIndex futureIndex;

    ZhihuIndexManager() {
        File files[] = Config.INDEX_DIR.listFiles();
        long maxVersion = 0;
        for(File f:files) {
            String name = f.getName();
            try {
                long version = Long.parseLong(name);
                if(version > maxVersion)
                    maxVersion = version;
            } catch (Exception e) {
            }
        }
        if(maxVersion != 0) {
            currentIndex = new ZhihuIndex(maxVersion);
            currentIndex.prepareDone();
        }
    }

    public synchronized boolean startNewBatch() {
        if(futureIndex != null)
            return false;
        futureIndex = new ZhihuIndex(TimeUtil.getLongTime());
        return true;
    }

    public synchronized boolean batchDone() {
        if(futureIndex == null || futureIndex.getStatus() != ZhihuIndex.Status.PREPARE)
            return false;
        futureIndex.prepareDone();
        if(currentIndex != null)
            currentIndex.stop();
        currentIndex = futureIndex;
        futureIndex = null;
        return true;
    }

    public void commitIndex() {
        synchronized (this) {
            if(this.currentIndex != null)
                this.currentIndex.flush();
        }

        synchronized (this) {
            if(this.futureIndex != null)
                this.currentIndex.flush();
        }
    }


    public void mayRefresh() {
        synchronized (this) {
            if(this.currentIndex != null)
                try {
                    this.currentIndex.getNrtManager().maybeRefresh();
                } catch (IOException e) {
                    logger.error("error while refrensh", e);
                }
        }

        synchronized (this) {
            if(this.futureIndex != null)
                try {
                    this.futureIndex.getNrtManager().maybeRefresh();
                } catch (IOException e) {
                    logger.error("error while refrensh", e);
                }
        }
    }

    public boolean isBatch() {
        if(futureIndex != null && futureIndex.getStatus() == ZhihuIndex.Status.PREPARE)
            return true;
        return false;
    }


    public void addIncrementDoc(DocumentSupport doc) {
        if(doc != null)
            this.addIncrementDoc(doc.toDocument());
    }

    public void addIncrementDoc(Document doc) {
        synchronized (this) {
            if(this.currentIndex != null)
                this.currentIndex.addDocument(doc);
        }

        synchronized (this) {
            if(this.futureIndex != null)
                this.futureIndex.addDocument(doc);
        }
    }

    public void addBatchDoc(DocumentSupport doc) {
        if(doc != null)
            this.addBatchDoc(doc.toDocument());
    }

    public void addBatchDoc(Document doc) {
        synchronized (this) {
            if(this.futureIndex != null)
                this.futureIndex.addDocument(doc);
        }
    }

    public void delDoc(Query query) {
        synchronized (this) {
            if(this.futureIndex != null)
                this.futureIndex.deleteDocument(query);
        }

        synchronized (this) {
            if(this.currentIndex != null)
                this.currentIndex.deleteDocument(query);
        }
    }

    public MultiNRTSearcherAgent acquire() {
        if(currentIndex == null) {
            logger.warn("no index ready for search");
            return null;
        }

        MultiNRTSearcherAgent agent = null;

        try {
            agent = currentIndex.getNrtManager().acquire();
        } catch (IOException e) {
            logger.error("error acquiring search agent", e);
            agent = null;
        }
        return agent;
    }

    public void release(MultiNRTSearcherAgent agent) {
        try {
            ZhihuNRTManager.release(agent);
        } catch (IOException e) {
            logger.error("error releasing search agent", e);
        }
    }

    public long currentIndexVersion() {
        if(currentIndex != null)
            return  currentIndex.getIndexVersion();
        return -1;
    }


    public long futureIndexVersion() {
        if(futureIndex != null)
            return  futureIndex.getIndexVersion();
        return -1;
    }
}

package com.eli.index.manager;

import com.eli.index.DocumentSupport;
import com.eli.util.Config;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 9/12/12
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZhihuIndex {



    private static final Logger logger = Logger.getLogger(ZhihuIndex.class);
    

    private Directory ramDirectory;

    private IndexWriter ramWriter;

    private ZhihuNRTManager nrtManager;
    private AtomicBoolean isIndexing;


    private File indexParentDir;
    private File indexDir;
    private long indexVersion;

    ZhihuIndex(long version) {
        this.indexVersion = version;
        this.indexParentDir = Config.INDEX_DIR;
        this.indexDir = new File(this.indexParentDir.getAbsolutePath() + File.separator + indexVersion);
        try{
            Directory directory = MMapDirectory.open(indexDir);
            ramDirectory = new RAMDirectory();
            //directory = FSDirectory.open(indexDir);
            ramWriter = new IndexWriter(ramDirectory, Config.getConfig());
            logger.info("load index to ram");
            ramWriter.addIndexes(directory);
            logger.info("load finished");
            directory.close();

            nrtManager = new ZhihuNRTManager(ramWriter);
            isIndexing = new AtomicBoolean();
            isIndexing.set(false);
        } catch (IOException e) {
            logger.error("failed to open index directory " + indexDir, e);
        }
    }

    public synchronized void startBuildIndex() {
        isIndexing.set(true);
        nrtManager.close();
        nrtManager = null;

        try {
            ramWriter.close();
        } catch (IOException e) {
            logger.error("failed to close ramWriter", e);
        }

        try {
            ramDirectory.close();
        } catch (IOException e) {
            logger.error("failed to close ramDirectory", e);
        }
        ramDirectory = new RAMDirectory();
        try{
            ramWriter = new IndexWriter(ramDirectory, Config.getConfig());
            nrtManager = new ZhihuNRTManager(ramWriter);
        } catch (IOException e) {
            logger.error("failed to open index directory " + indexDir, e);
        }
    }


    public ZhihuNRTManager getNrtManager() {
            return nrtManager;
    }



    public long getIndexVersion() {
        return indexVersion;
    }

    public synchronized void addDocument(DocumentSupport doc)  {
        if(doc != null)
            addDocument(doc.toDocument());
    }


    public synchronized void addDocument(Document doc)  {

        try{

          //      logger.info("indexing documents:"+doc.toString());
                ramWriter.addDocument(doc);
   //             logger.info("indexing done");
        }catch (IOException e) {
            logger.error(e);
        }
    }

    public synchronized void deleteDocument(Query query){


        try{
       //     logger.info("delete from index:" + query);
            ramWriter.deleteDocuments(query);
   //         logger.info("delete done:"+query);
        }catch (IOException e) {
            logger.error(e);
        }

    }


    public synchronized void finishBuildIndex()  {
        try{
            ramWriter.commit();
            Directory directory = MMapDirectory.open(indexDir);
            IndexWriterConfig config = Config.getConfig();
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            logger.info("save index to disk");
            IndexWriter indexWriter = new IndexWriter(directory, config);
            indexWriter.addIndexes(ramDirectory);
            indexWriter.commit();
            indexWriter.close();
            logger.info("save index finished");
            isIndexing.set(false);
        }catch (IOException e) {
            logger.error(e);
        }
    }


    public synchronized void  commit() {
        try{
            ramWriter.commit();
        }catch (IOException e) {
            logger.error(e);
        }
    }

    public boolean getIsIndexing() {
        return isIndexing.get();
    }
}

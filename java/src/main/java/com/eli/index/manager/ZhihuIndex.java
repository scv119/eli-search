package com.eli.index.manager;

import com.eli.index.DocumentSupport;
import com.eli.util.Config;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 9/12/12
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZhihuIndex {



    private static final Logger logger = Logger.getLogger(ZhihuIndex.class);
    
    private Directory directory;
    private Directory ramDirectory;

    private IndexWriter iWriter;
    private IndexWriter ramWriter;

    private ZhihuNRTManager nrtManager;


    private File indexParentDir;
    private File indexDir;
    private long indexVersion;

    ZhihuIndex(long version) {
        this.indexVersion = version;
        this.indexParentDir = Config.INDEX_DIR;
        this.indexDir = new File(this.indexParentDir.getAbsolutePath() + File.separator + indexVersion);
        try{
            directory = MMapDirectory.open(indexDir);
            ramDirectory = new RAMDirectory();
            //directory = FSDirectory.open(indexDir);
            ramWriter = new IndexWriter(ramDirectory, Config.getConfig());
            logger.info("load index to ram");
            ramWriter.addIndexes(directory);
            logger.info("load finished");
            iWriter   = new IndexWriter(directory, Config.getConfig());
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

    public void addDocument(DocumentSupport doc)  {
        if(doc != null)
            addDocument(doc.toDocument());
    }


    public void addDocument(Document doc)  {

        try{

          //      logger.info("indexing documents:"+doc.toString());
                iWriter.addDocument(doc);
                ramWriter.addDocument(doc);
                logger.info("indexing done");
        }catch (IOException e) {
            logger.error(e);
        }
    }

    public void deleteDocument(Query query){


        try{
       //     logger.info("delete from index:" + query);
            iWriter.deleteDocuments(query);
            ramWriter.deleteDocuments(query);
            logger.info("delete done:"+query);
        }catch (IOException e) {
            logger.error(e);
        }

    }


    public void flush()  {
        this.commit();
    }


    private void commit() {
        try{
            iWriter.commit();
            ramWriter.commit();
        }catch (IOException e) {
            logger.error(e);
        }
    }

    public void stop() {
        try{
            iWriter.close();
            ramWriter.close();
        }catch (IOException e) {
            logger.error(e);
        }
    }

}

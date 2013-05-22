package com.eli.util;

import com.eli.index.IndexAnalyzerBuilder;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.util.Version;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 6/15/12
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */


public class Config {
    private final static Logger logger = Logger.getLogger(Config.class);
    private final static ConfigurationLoader       configLoader = ConfigurationLoader.INSTANCE;

    public static boolean                   DEBUG = configLoader.getBoolean("debug", true);
    public static final File                INDEX_DIR = new File(configLoader.get("idx_path","index"));
    public static final String              FILE_PATH = configLoader.get("file_path","./test.file");
    public static final int                 PORT = configLoader.getInteger("listen_port",8080);
    public static final int                 MAX_IDLE_COUNT = configLoader.getInteger("max_idle_count",5);
    public static final int                 MAX_ACTIVE_COUNT = configLoader.getInteger("max_active_count",10);



    public static final int                 THREAD_NUM = configLoader.getInteger("thread_num", 50);
    public static final long                MEM_LIMIT = configLoader.getLong("mem_limit", 1024L*1024*10);

    public static final String[]            IMAGE_BASE_URL = new String[]{"p1.zhimg.com","p2.zhimg.com","p3.zhimg.com","p4.zhimg.com"};


    public static final Version version = Version.LUCENE_36;


    public static final Analyzer default_analyzer = new StandardAnalyzer(version);
    public static final Analyzer pinyin_analyzer  = new StandardAnalyzer(version);
    public static final Analyzer us_prefix_analyzer = new StandardAnalyzer(version);

    public static IndexWriterConfig getConfig(){
        IndexWriterConfig config = new IndexWriterConfig(version, IndexAnalyzerBuilder.getIndexAnalyzer());
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        return config;
    }

    public static File getActionXml() throws URISyntaxException {
        String path = "src/main/resources/url.xml";
        File file = new File(path);
        return file;
    }
}

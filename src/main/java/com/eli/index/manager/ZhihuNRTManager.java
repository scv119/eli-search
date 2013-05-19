package com.eli.index.manager;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager;

import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 6/19/12
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZhihuNRTManager {

    private static final Logger logger = Logger.getLogger(ZhihuNRTManager.class);
    private NRTManager managers[];
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private static final Map<ZhihuNRTManager, Object> refreshManagers = new WeakHashMap<ZhihuNRTManager, Object>();
    private static final Object EMPTY = new Object();

    static {
        startPeriodRefresh();
    }


    ZhihuNRTManager(IndexWriter ...writers){
        try{
            managers = new NRTManager[writers.length];
            for(int i = 0; i < writers.length; i ++) {
                managers[i] = new NRTManager(new NRTManager.TrackingIndexWriter(writers[i]), null ,true);
            }
            synchronized (refreshManagers) {
                refreshManagers.put(this, EMPTY);
            }
        }catch(IOException e){
            logger.error("failed to open Zhihu Index Writer in NRTManager",e);
        }

    }

    public MultiNRTSearcherAgent acquire() throws IOException {
        IndexSearcher[] searchers = new IndexSearcher[managers.length];
        for(int i = 0; i < searchers.length; i++) {
            searchers[i] = managers[i].acquire();
        }
        return new MultiNRTSearcherAgent(this, searchers);
    }

    public static void release(MultiNRTSearcherAgent searcherAgent) throws IOException {
        IndexSearcher searchers[] = searcherAgent.getReleaseSearchers();
        for(int i = 0; i < searchers.length; i++) {
             searcherAgent.getNrtManger().managers[i].release(searchers[i]);
        }
        searcherAgent.getSearcher().close();
    }

    public void maybeRefresh() throws IOException{
        for(NRTManager manager:managers)
            manager.maybeRefresh();
    }

    public static void  startPeriodRefresh(){
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                synchronized (refreshManagers) {
                    for(ZhihuNRTManager manager: refreshManagers.keySet()) {
                        try{
                            manager.maybeRefresh();
                        }catch(IOException e){
                            logger.error("NRT refresh thread error", e);
                        }
                    }
                }

            }
        },0, 1, TimeUnit.SECONDS);
    }

    public static  void stopPeriodRefresh(){
        scheduledExecutorService.shutdown();
    }

}

package com.eli.util;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 9/18/12
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThroughputMonitor {
    private ScheduledExecutorService service;
    private Caller caller;
    private long   qps;
    private long   pre;


    public ThroughputMonitor(Caller caller) {
        service = new ScheduledThreadPoolExecutor(1);
        this.caller = caller;
        qps = 0;
        pre = 0;

        start();
    }

    private void start() {
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long count = caller.getValue();
                qps  = count - pre;
                pre = count;
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        service.shutdown();
    }

    public long getQps() {
        return qps;
    }

    public interface Caller {
        long getValue();
    }

}

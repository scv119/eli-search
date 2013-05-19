package com.eli.bin;

import com.eli.web.HttpServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 7/23/12
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewSearch {
    public static void main(String args[]){
        final HttpServer server = new HttpServer();

        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                server.run();
            }
        });
    }
}

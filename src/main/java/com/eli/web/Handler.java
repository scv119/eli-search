package com.eli.web;

import com.eli.util.ThroughputMonitor;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 6/21/12
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Handler extends SimpleChannelUpstreamHandler {

    private ActionProxy actionProxy;

    private static AtomicLong queryCount = new AtomicLong(0L);

    private static ThroughputMonitor monitor = new ThroughputMonitor(new ThroughputMonitor.Caller() {
        @Override
        public long getValue() {
            return  Handler.queryCount.get();
        }
    });



    public Handler(){
        actionProxy = ActionProxy.getInstance();

    }

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        queryCount.addAndGet(1L);
        HttpRequest request = (HttpRequest) e.getMessage();
        HttpResponse resp = actionProxy.execute(request);


        e.getChannel().write(resp).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                future.getChannel().close();
            }});
    }

    public static long getQPS() {
        return monitor.getQps();
    }

}

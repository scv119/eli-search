package com.eli.web;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.execution.ExecutionHandler;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 6/21/12
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class PipelineFactory implements ChannelPipelineFactory {
    private final ExecutionHandler executionHandler;

    public PipelineFactory(ExecutionHandler executionHandler) {
        this.executionHandler = executionHandler;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        return Channels.pipeline(new HttpRequestDecoder(),
                                new HttpResponseEncoder(),
                                new Handler(),
                                new HttpResponseEncoder());
    }
}
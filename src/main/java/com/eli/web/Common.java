package com.eli.web;

import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 7/9/12
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Common {
    public static final HttpResponse NOT_FOUND =  new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
    public static final HttpResponse INTERNAL_SERVER_ERROR =  new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);

}

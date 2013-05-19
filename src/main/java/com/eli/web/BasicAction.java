package com.eli.web;

import com.eli.web.view.JsonEngine;
import com.eli.web.view.StaticEngine;
import com.eli.web.view.TemplateEngine;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.handler.codec.http.multipart.Attribute;
import org.jboss.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.jboss.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 6/21/12
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BasicAction {
    private static final Logger logger = Logger.getLogger(BasicAction.class);
    private Map<String,String> params;
    private HttpPostRequestDecoder httpPostRequestDecoder;
    protected HttpRequest httpRequest;
    protected HttpResponse httpResponse;
    private Map root;
    private String param;
    private TemplateEngine ftlEngine = TemplateEngine.INSTANCE;
    private JsonEngine jsonEngine = JsonEngine.INSTANCE;
    private StaticEngine staticEngine = StaticEngine.INSTANCE;
    private Type type;
    private Map<String,String> cookies;
    private String referer;

    protected void setParam(String para){
        this.param = para;
    }

    protected String getReferer() {
        return referer;
    }

    protected void setType(String type){
        this.type = Type.valueOf(type);
    }

    protected String getParam(String key, String defaultValue){
        String result = defaultValue;
        if(this.params.containsKey(key))
            result = this.params.get(key);
        else if(httpPostRequestDecoder != null) {
            try{
                InterfaceHttpData data = httpPostRequestDecoder.getBodyHttpData(key);
                if (data != null && data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                    Attribute attribute = (Attribute) data;
                    result = attribute.getValue();
                }
            }catch (Exception e) {
                logger.warn("failed to get post field:"+ key, e);
            }
        }

        return com.eli.util.Common.html_escape(result);
    }

    protected String getFiltedParam(String key, String defaultValue){
        String value = getParam(key, defaultValue);
        return com.eli.util.Common.parseString(value, false, true);
    }

    protected String getFiltedParamCaseInsensitive(String key, String defaultValue){
        String value = getParam(key, defaultValue);
        return com.eli.util.Common.parseString(value, false, true, false);
    }

    protected String getCookie(String key) {
        if(cookies != null)
            return cookies.get(key);
        return null;
    }



    private void parseHeads() {
        String cookies = httpRequest.getHeader("Cookie");
        if(cookies != null) {
            this.cookies = new HashMap<String,String>();
            String arrs[] = cookies.split(";");
            for(String s:arrs) {
                int idx = s.indexOf("=");
                if(idx >= 0) {

                    String key = s.substring(0,idx).trim(); String value = s.substring(idx+1, s.length()).trim();
                    if(value.startsWith("\""))
                        value = value.substring(1);
                    if(value.endsWith("\""))
                        value = value.substring(0, value.length()-1);

                    if(key.length() > 0 && value.length() > 0)
                        this.cookies.put(key, value) ;
                }
            }
        }

        referer = httpRequest.getHeader("Referer");
    }

    private void parseHttpRequest() throws HttpPostRequestDecoder.IncompatibleDataDecoderException, HttpPostRequestDecoder.ErrorDataDecoderException, HttpPostRequestDecoder.NotEnoughDataDecoderException {
        parseHeads();
        if(httpRequest.getMethod().equals(HttpMethod.POST)) {
            httpPostRequestDecoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), httpRequest);
        }
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(httpRequest.getUri());
        Map<String, List<String>> rawParams = queryStringDecoder.getParameters();
        params = new HashMap<String,String>();
        for (String key:rawParams.keySet()){
            params.put(key,rawParams.get(key).get(0));
        }
        root = new HashMap();
    }

    protected void put(String key, Object o){
        root.put(key, o);
    }

    protected void beforeExecute() {};

    protected void afterExecute() {};

    public HttpResponse process(HttpRequest request) throws IOException, TemplateException, HttpPostRequestDecoder.IncompatibleDataDecoderException, HttpPostRequestDecoder.ErrorDataDecoderException, HttpPostRequestDecoder.NotEnoughDataDecoderException {
        this.httpRequest = request;
        this.httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        httpResponse.addHeader("Content-Type","text/html; charset=utf-8");

        this.parseHttpRequest();

        this.beforeExecute();

        this.execute();

        this.afterExecute();


        String html = null;
        if(type == Type.ftl)
            html = this.ftlEngine.render(param,root);
        else if (type == Type.json)
            html = this.jsonEngine.render(param,root);
        else
            html = this.staticEngine.render(param);

        httpResponse.setContent(ChannelBuffers.copiedBuffer(html, Charset.defaultCharset()));

        return httpResponse;
    }

    protected abstract void execute() throws IOException;

    public enum Type{
        json, ftl, file;
    }
}

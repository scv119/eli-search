package com.eli.web;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import com.eli.util.Config;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 6/21/12
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActionProxy {
    private final static Logger logger = Logger.getLogger(ActionProxy.class);
    private Document dom;
    private Map<String,Constructor> constructorMaps;
    private Map<String,Map<String,String>> paraMaps;

    private static ActionProxy instance = null;

    public static synchronized ActionProxy getInstance() {
        if(instance == null)
            instance = new ActionProxy();
        return instance;
    }

    private ActionProxy(){
        constructorMaps = new HashMap<String,Constructor>();
        paraMaps = new HashMap<String, Map<String, String>>();
        try{
                parseXml();
        }catch(Exception e){
            logger.error("failed to parse xml",e);
        }
    }

    private void parseXml() throws DocumentException, ClassNotFoundException, NoSuchMethodException, URISyntaxException {
        SAXReader reader = new SAXReader();
        dom = reader.read(Config.getActionXml());
        for(Iterator i = dom.getRootElement().elementIterator("action");i.hasNext();){
            Element ele = (Element)i.next();
            String url = ele.element("url").getText();
            String clazz = ele.element("class").getText();
            Map<String,String> para = new HashMap<String,String>();
            para.put("param",ele.element("param").getText());
            para.put("type",ele.element("type").getText());
            Class cls = Class.forName(clazz);
            Constructor cst = cls.getConstructor();
            constructorMaps.put(url, cst);
            paraMaps.put(url, para);
        }

    }

    private BasicAction getAction(String uri){
        BasicAction result = null;
        if(uri.indexOf("?")>=0){
            uri = uri.substring(0,uri.indexOf("?"));
        }
        if(constructorMaps.containsKey(uri)){
            Constructor cst = constructorMaps.get(uri);
            try {
                result = (BasicAction)cst.newInstance();
                result.setParam(paraMaps.get(uri).get("param"));
                result.setType(paraMaps.get(uri).get("type"));
            } catch (InstantiationException e) {
                logger.error("failed to instance action",e);
            } catch (IllegalAccessException e) {
                logger.error("failed to instance action",e);
            } catch (InvocationTargetException e) {
                logger.error("failed to instance action",e);
            }
        }

        if (result == null) {
            result = new FileAction();
            result.setParam(uri);
        }

        return result;
    }

    public HttpResponse execute(HttpRequest req){
        BasicAction action = this.getAction(req.getUri());
        HttpResponse resp = null;

        if(action != null)  {
            long begin = com.eli.util.Common.getTime();
            try{
                resp = action.process(req);
            }catch(Exception e){
                logger.error("500 "+req.toString(),e);
                resp = Common.INTERNAL_SERVER_ERROR;
            }
            long end = com.eli.util.Common.getTime();
        }
        if(resp == null){
            logger.error("404 "+req.toString());
            resp = Common.NOT_FOUND;
        }

        logger.debug(resp.getStatus()+"\r\n"+req.toString());
        return resp;
    }
}

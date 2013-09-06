package com.eli.web.view;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 7/19/12
 * Time: 7:15 PM
 * To change this template use File | Settings | File Templates.
 */
public enum TemplateEngine {
    INSTANCE;

    private Configuration cfg;


    TemplateEngine() {

        try {
            cfg = new Configuration();
            cfg.setDirectoryForTemplateLoading(new File("./templates"));
            cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
            cfg.setObjectWrapper(new DefaultObjectWrapper());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String render(String patternPath, Map root) throws IOException, TemplateException {
        Template temp = cfg.getTemplate(patternPath);
        StringWriter writer = new StringWriter();
        temp.process(root,writer);
        writer.flush();
        return writer.toString();
    }
}

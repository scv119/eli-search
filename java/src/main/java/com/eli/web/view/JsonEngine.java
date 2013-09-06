package com.eli.web.view;

import com.google.gson.Gson;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 7/19/12
 * Time: 7:15 PM
 * To change this template use File | Settings | File Templates.
 */
public enum JsonEngine {
    INSTANCE;

    private Gson gson;


    JsonEngine() {
        gson = new Gson();
    }

    public String render(String param, Map root) throws IOException, TemplateException {
        String json = gson.toJson(root.get(param));
        return json;
    }
}

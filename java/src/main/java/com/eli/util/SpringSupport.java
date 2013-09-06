package com.eli.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 8/14/12
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpringSupport {
    private static ApplicationContext context = null;
    public synchronized static ApplicationContext getContext(){
        if(context == null)
            context = new ClassPathXmlApplicationContext(new String[]{"spring-config.xml"});
        return context;
    }

    public static Object getBean(String name){
        getContext();
        return context.getBean(name);
    }
}

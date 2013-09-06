package com.eli.index;

import com.eli.index.document.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 8/11/12
 * Time: 8:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexAnalyzerBuilder {

    private static PerFieldAnalyzerWrapper wrapper = null;
    private static Map<String,Analyzer> analyzerMap = new HashMap<String,Analyzer>();

    public static synchronized PerFieldAnalyzerWrapper getIndexAnalyzer(){
        if(wrapper == null)
            buildWrapper();
        return wrapper;
    }

    private static void buildWrapper(){
        initAnalyzerMap();
        wrapper = new PerFieldAnalyzerWrapper(new StandardAnalyzer(Version.LUCENE_36), analyzerMap);
    }

    public static void initAnalyzerMap(){
        for(Class clazz:IndexConfig.registeredClasses){
            Field[] fields = clazz.getDeclaredFields();
            for(Field field:fields){
                IField iField = field.getAnnotation(IField.class);
                if(iField != null){
                    for(IndexType it:iField.indexTypes())
                        registerField(field.getName(), it);
                }
            }
        }
    }

    public static void registerField(String field, IndexType type){
        field = field + "." + type.name();
        analyzerMap.put(field, AnalyzerFactory.getAnalyzer(type, AnalyzerFactory.Usage.INDEX));
    }
}

package com.eli.index;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 8/13/12
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class AnalyzerFactory {
    private static final Logger logger = Logger.getLogger(AnalyzerFactory.class);
    private static Map<Class, Analyzer> instanceCache = new HashMap<Class, Analyzer>();


    public static enum Usage {
        INDEX;
    }

    public static Analyzer getAnalyzer(IndexType type, Usage usage) {
        Analyzer ret = null;
        try {
            switch (type) {
                case NGRAM:
                    ret = getInstance(CJKAnalyzer.class, Version.LUCENE_36);
                    break;
                default:
                    ret = getInstance(StandardAnalyzer.class, Version.LUCENE_36);
            }
        } catch (Exception e) {
            logger.error("instance analyzer error:" + type.name() + " " + usage.name(), e);
        }
        return ret;
    }

    private static synchronized Analyzer getInstance(Class clazz, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        if (!instanceCache.containsKey(clazz)) {
            Class params[] = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                params[i] = args[i].getClass();
            }
            Constructor cons = clazz.getConstructor(params);
            Object obj = cons.newInstance(args);
            Analyzer instance = (Analyzer) obj;
            instanceCache.put(clazz, instance);
        }
        return instanceCache.get(clazz);
    }
}

package com.eli.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 6/15/12
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ConfigurationLoader {
    INSTANCE;

    private Properties properties;
    private Logger logger;

    ConfigurationLoader(){

        logger = Logger.getLogger(ConfigurationLoader.class);

        properties = new Properties();
        String pName =  "src/main/resources/config.properties";
        FileReader fr = null;
        try {
            File f = new File(pName);
            fr = new FileReader(f);
            properties.load(fr);

        }catch (IOException e){
            logger.error("unable to load config file "+ pName, e);
        }
        finally {
            try{
                fr.close();
            }catch(IOException e){
            }
        }
    }

    public String get(String key, String defaultRet){
        if(properties.containsKey(key))
            return properties.getProperty(key);
        logger.error("failed to load configuration "+key+", using "+ defaultRet);
        return defaultRet;
    }

    public Integer getInteger(String key, Integer defaultRet){
        String value = this.get(key, null);
        try{
            return Integer.parseInt(value);
        }catch(Exception e){
            logger.error("failed to load configuration "+key+", using "+ defaultRet, e);
        }
        return defaultRet;
    }

    public Boolean getBoolean(String key, Boolean defaultRet){
        String value = this.get(key, null);
        try{
            return Boolean.parseBoolean(value);
        }catch(Exception e){
            logger.error("failed to load configuration "+key+", using "+ defaultRet, e);
        }
        return defaultRet;
    }

    public Long getLong(String key, Long defaultRet){
        String value = this.get(key, null);
        try{
            return Long.parseLong(value);
        }catch(Exception e){
            logger.error("failed to load configuration "+key+", using "+ defaultRet, e);
        }
        return defaultRet;
    }

    public Float getFloat(String key, Float defaultRet) {
        String value = this.get(key, null);
        try{
            return Float.parseFloat(value);
        }catch(Exception e){
            logger.error("failed to load configuration "+key+", using "+ defaultRet, e);
        }
        return defaultRet;
    }
}

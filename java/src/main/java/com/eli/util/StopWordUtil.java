package com.eli.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wangxiao
 * Date: 12-7-30
 * Time: 下午4:21
 * To change this template use File | Settings | File Templates.
 */
public enum StopWordUtil {
    INSTANCE;

    private String filePath = "src/main/resources/dict/stopwords.dic";
    private Map<String,Boolean> stopMap = new HashMap<String, Boolean>();
    private void init() {
        File stopFile = new File(filePath);
        try {
            FileReader fileReader = new FileReader(stopFile);
            BufferedReader br = new BufferedReader(fileReader);
            String word;
            while ( (word = br.readLine()) != null ) {
                stopMap.put(word,true);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private StopWordUtil (){
        init();
    }

    public boolean isStopWord ( String word ) {
        if(this.stopMap.containsKey(word)) {
            return true;
        }
        return false;
    }
}
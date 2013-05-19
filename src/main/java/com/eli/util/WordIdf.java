package com.eli.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: wangxiao
 * Date: 12-9-25
 * Time: 下午8:46
 */
public enum WordIdf {
    INSTANCE;
    private static final Logger logger = Logger.getLogger(WordIdf.class);


     /**
     * Open a file and read every line
     *
     * @param filePath file path String
     * @return List string ever line
     */
    public static List<String> readFileLines(String filePath) {
        File file = new File(filePath);
        List<String> result = new ArrayList<String>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                result.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
        return result;
    }
    private Map<String, Double> idfMap = new HashMap<String, Double>();

    private void initMap() {
        List<String> lines = readFileLines("src/main/resources/dict/word_idf.list");
        for (String line : lines) {
            String[] strings = line.split("\t");
            if (strings.length > 1) {
                idfMap.put(strings[0], Double.parseDouble(strings[1]));
            }
        }
    }

    private WordIdf() {
        initMap();
    }

    public Double getIdf(String w) {
        if (this.idfMap.containsKey(w) == false) {
            return 10.0;
        }
        return this.idfMap.get(w);
    }
}

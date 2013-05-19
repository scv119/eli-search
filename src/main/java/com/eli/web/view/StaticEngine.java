package com.eli.web.view;

import com.eli.util.Config;
import org.apache.log4j.Logger;
import org.apache.lucene.util.collections.LRUHashMap;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 11/13/12
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public enum StaticEngine {
    INSTANCE;

    private static final Logger LOG = Logger.getLogger(StaticEngine.class.getName());
    
    private String location = "./static";
    private LRUHashMap<String, String> cache;
    private static final int CACHE_COUNT = 100;


    StaticEngine() {
        cache = new LRUHashMap<String, String>(Config.DEBUG ? 0 : CACHE_COUNT);
    }

    public String render(String patternPath) throws IOException{
        String resp = cache.get(patternPath);

        if (resp == null) {
            resp = cache.get(patternPath);
            if (resp == null)
                resp = loadStatic(patternPath);
        }

        return resp;
    }

    private String loadStatic(String path) {
        File f = new File(location + File.separator + path);
        String ret = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\r\n");
            }
            ret = sb.toString();
            synchronized (cache) {
                if (!cache.containsKey(path))
                    cache.put(path, ret);
            }
        } catch (FileNotFoundException e) {
            LOG.error("file not found " + f.getAbsolutePath());
            ret = "404 File not found";
        } catch (IOException e) {
            LOG.warn("failed to read file " + f.getAbsolutePath() , e);
            ret = "500 Internal Error";
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                }
        }

        return ret;
    }

}

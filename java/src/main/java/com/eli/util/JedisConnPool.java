package com.eli.util;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 7/17/12
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class JedisConnPool {
    static Map<String,JedisConnPool> instanceMap = new HashMap<String,JedisConnPool>();

    private String host;
    private int port;
    private int db;

    private ThreadLocal<Jedis> jedisThreadLocal = new ThreadLocal<Jedis>();


    private JedisConnPool(String host, int port, int db){
        this.host = host;
        this.port = port;
        this.db   = db;
    }



    public Jedis getJedis(){
        Jedis client = null;
        client = jedisThreadLocal.get();
        if(!validJedis(client))
        {
            close(client);
            client = new Jedis(host, port);
            client.select(db);
            jedisThreadLocal.set(client);
        }
        return client;
    }

    private boolean validJedis(Jedis client){
        if(client != null && client.isConnected()){
            return true;
        }
        return false;
    }

    private void close(Jedis client){
        try{
            if(client != null)
                client.disconnect();
        } catch (Exception e){
        }
    }

    public static synchronized JedisConnPool getInstance(String host, int port ,int db){
        String key = host.trim()+":"+port+":"+db;
        if(!instanceMap.containsKey(key))
            instanceMap.put(key, new JedisConnPool(host, port, db));
        return instanceMap.get(key);
    }

    public static synchronized JedisConnPool getInstance(){
        return getInstance("localhost",6379,0);
    }
}

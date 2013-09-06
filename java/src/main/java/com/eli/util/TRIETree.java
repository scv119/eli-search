package com.eli.util;

import com.eli.index.controller.CacheMemberDao;
import com.eli.index.controller.Member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 13-8-10
 * Time: PM5:36
 * To change this template use File | Settings | File Templates.
 */
public class TRIETree<T> {
    private boolean caseSensitive = true;
    private Node root;

    public TRIETree(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        root = new Node();
    }


    public void put(String key, T value) {
        key = processKey(key);
        Node current = root;
        for(int i = 0; i < key.length(); ++i) {
            char c = key.charAt(i);
            if (!current.map.containsKey(c)) {
                current.map.put(c, new Node());
            }
            current = current.map.get(c);
        }
        current.values.add(value);
    }

    public List<T> getValues(String key, int limit) {
        List<T> ret = new ArrayList<T>();
        key = processKey(key);
        Node current = root;
        for (int i = 0; i < key.length(); i ++) {
            char c = key.charAt(i);
            if (!current.map.containsKey(c))
                return ret;
            current = current.map.get(c);
        }
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(current);
        while(!queue.isEmpty() && ret.size() < limit) {
            Node node = queue.poll();
            for(T item:node.values) {
                ret.add(item);
                if(ret.size() == limit)
                    break;
            }
            for(Character k:node.map.keySet()) {
                queue.add(node.map.get(k));
            }
        }
        return ret;
    }


    private String processKey (String key) {
        if (!caseSensitive) {
            key = key.toLowerCase();
        }
        return key;
    }

    class Node {
        List<T> values;
        Map<Character, Node> map;

        Node() {
            map = new HashMap<Character, Node>();
            values = new LinkedList<T>();
        }
    }


    public static void main(String args[]) {
        TRIETree<Member> trieTree = new TRIETree<Member>(true);
        CacheMemberDao memberDao = CacheMemberDao.INSTANCE;
        for (Member member : memberDao.getMembers()) {
            trieTree.put(member.getName(), member);
        }

        try{
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));

            String input;

            while((input=br.readLine())!=null){
               // System.out.println(input);
                List<Member> ret = trieTree.getValues(input, 10);
                for (Member member : ret) {
                    System.out.println(member.getName());
                }
            }

        }catch(IOException io){
            io.printStackTrace();
        }
    }
}

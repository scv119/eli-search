package com.eli.index.controller;

/**
 * Created with IntelliJ IDEA.
 * User: scv119
 * Date: 13-5-19
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
public class Discussion implements Comparable{
    public int id;
    public int topicId;
    public int topicSort;
    public String  title;
    public String content;
    public String date;

    public Discussion(int id, int topicId, int topicSort, String title, String content, String date) {
        this.id = id;
        this.topicId = topicId;
        this.title = title;
        this.topicSort = topicSort;
        this.content = content;
        this.date = date;
    }

    @Override
    public int compareTo(Object o) {
       return this.date.compareTo(((Discussion)o).date);
    }
}

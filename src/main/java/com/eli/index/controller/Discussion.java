package com.eli.index.controller;

/**
 * Created with IntelliJ IDEA.
 * User: scv119
 * Date: 13-5-19
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
public class Discussion {
    public int id;
    public int topicId;
    public String  author;
    public String content;

    public Discussion(int id, int topicId, String author, String content) {
        this.id = id;
        this.topicId = topicId;
        this.author = author;
        this.content = content;
    }
}

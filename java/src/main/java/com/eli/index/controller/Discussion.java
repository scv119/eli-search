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
    public long date;
    public int boardId;
    public int readCount;
    public int author;

    public Discussion(int id, int topicId, int topicSort, int boardId, String title, String content, String date) {
        this.boardId = boardId;
        this.id = id;
        this.topicId = topicId;
        this.title = title;
        this.topicSort = topicSort;
        this.content = content;
        this.date = Long.parseLong(date);
    }

    @Override
    public int compareTo(Object o) {
        long diff = this.date - (((Discussion)o).date);
        return diff < 0 ? -1 : (diff == 0? 0 : 1);
    }
}

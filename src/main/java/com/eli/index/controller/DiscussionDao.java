package com.eli.index.controller;

import com.eli.index.document.DiscussionDoc;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: scv119
 * Date: 13-5-19
 * Time: 下午1:57
 * To change this template use File | Settings | File Templates.
 */
public interface DiscussionDao {
    public List<Discussion> getNextDiscussion();
}

package com.eli.index.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: scv119
 * Date: 13-5-19
 * Time: 下午2:05
 * To change this template use File | Settings | File Templates.
 */
public class FileDiscussionDao implements DiscussionDao {
    @Override
    public List<Discussion> getNextDiscussion() {
       List<Discussion> ret = new ArrayList<Discussion>();
        return ret;
    }
}

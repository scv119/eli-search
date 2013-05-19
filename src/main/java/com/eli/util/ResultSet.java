package com.eli.util;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 7/16/12
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResultSet<T> extends ArrayList<T> {
    private int totalHits;
    private int offset;

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}

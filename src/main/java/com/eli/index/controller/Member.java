package com.eli.index.controller;

/**
 * Created with IntelliJ IDEA.
 * User: scv119
 * Date: 13-6-26
 * Time: 下午11:39
 * To change this template use File | Settings | File Templates.
 */
public class Member {
    private int id;
    private String name;
    private String aliasName;
    private String avatar = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

package com.eli.index.controller;

import com.eli.util.TRIETree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 13-8-4
 * Time: AM11:02
 * To change this template use File | Settings | File Templates.
 */
public enum CacheMemberDao implements MemberDao{
    INSTANCE;

    private MemberDao memberDao;
    private Map<Integer, Member> memberMap;
    private Map<String, Integer> nameToId;
    private TRIETree<Member> trieTree;

    private CacheMemberDao () {
        this.memberDao = new FileMemberDao();
        List<Member> members = this.memberDao.getMembers();
        this.memberMap = new HashMap<Integer, Member>();
        this.nameToId = new HashMap<String, Integer>();
        this.trieTree = new TRIETree<Member>(false);
        for (Member m : members) {
            memberMap.put(m.getId(), m);
            nameToId.put(m.getName(), m.getId());
            trieTree.put(m.getName(), m);
        }
    }
    @Override
    public List<Member> getMembers() {
        List<Member> list = new ArrayList<Member>();
        list.addAll(memberMap.values());
        return list;
    }

    public Member getMember(int memberId) {
        if (memberMap.containsKey(memberId))
            return memberMap.get(memberId);
        return null;
    }

    public int getMemberId(String name) {
        if (nameToId.containsKey(name))
            return nameToId.get(name);
        return 0;
    }

    public List<Member> getMemberByPrefix(String prefix, int limit) {
        return this.trieTree.getValues(prefix, limit);
    }
}

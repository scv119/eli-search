package com.eli.index.controller;

import com.eli.index.document.MemberDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: scv119
 * Date: 13-6-26
 * Time: 下午11:53
 * To change this template use File | Settings | File Templates.
 */
public class MemberController {
    private MemberDao memberDao;

    public MemberController() {
        this.memberDao = CacheMemberDao.INSTANCE;
    }

    public List<MemberDoc> getMembers() {
        List<MemberDoc> list = new ArrayList<MemberDoc>();
        List<Member> members = this.memberDao.getMembers();
        for (Member member : members) {
            MemberDoc doc = new MemberDoc();
            doc.setUrl("http://new.elimautism.org/User/LookUserInfo.asp?id=" + member.getId());
            doc.setName(member.getName());
            doc.setAvatar(member.getAvatar());
            list.add(doc);
        }
        return list;
    }
}

package com.eli.web.action;

import com.eli.index.controller.CacheMemberDao;
import com.eli.index.controller.Member;
import com.eli.index.manager.ZhihuIndexManager;
import com.eli.index.offline.BuildIndex;
import com.eli.web.BasicAction;
import com.eli.web.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 13-8-10
 * Time: PM6:08
 * To change this template use File | Settings | File Templates.
 */
public class AutoCompleteAction extends BasicAction {
    @Override
    protected void execute() {
        String token = super.getParam("q", "");
        CacheMemberDao memberDao  = CacheMemberDao.INSTANCE;
        List<Member> members = memberDao.getMemberByPrefix(token, 10);
        List<String> lists = new ArrayList<String>(members.size());
        for (Member member:members) {
            lists.add(member.getName());
        }
        super.put("list", lists);
    }
}

package com.eli.index.controller;

import com.eli.util.Common;
import com.eli.util.Config;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: scv119
 * Date: 13-5-19
 * Time: 下午2:05
 * To change this template use File | Settings | File Templates.
 */
public class FileMemberDao implements MemberDao {
    private static final Logger LOG = Logger.getLogger(FileMemberDao.class);


    @Override
    public List<Member> getMembers() {
        List<Member> ret = new ArrayList<Member>();
        BufferedReader br = null;
        try {
           br=new BufferedReader(new InputStreamReader(new FileInputStream(Config.MEMBER_PATH), "GBK"));
            String line;
            while ((line = br.readLine()) != null) {
                String tokens[] = line.split("\t");
                Member member = new Member();
                member.setId(Integer.parseInt(tokens[0]));
                member.setName(tokens[1]);
                if (tokens.length >= 3)
                    member.setAvatar(tokens[2]);
                ret.add(member);
            }
        } catch(IOException e) {
            LOG.error(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {}
        }


        try {
            br=new BufferedReader(new InputStreamReader(new FileInputStream(Config.JSON_MEMBER_PATH), "GBK"));
            String line;
            long applyTime = 0;
            while ((line = br.readLine()) != null) {
                JSONObject json = new JSONObject(line);
                Member member = new Member();
                member.setId(Integer.parseInt((String)json.get("ID")));
                member.setName(Common.decodeElin((String)json.get("UserName")));
                if (Long.parseLong((String)json.get("ApplyTime")) < applyTime)
                    continue;
                applyTime = Long.parseLong((String)json.get("ApplyTime"));
                ret.add(member);
            }
        } catch(Exception e) {
            LOG.error(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {}
        }



        return ret;
    }
}

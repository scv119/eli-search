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
public class FileDiscussionDao implements DiscussionDao {
    private static final Logger LOG = Logger.getLogger(FileDiscussionDao.class);
    private  Map<Integer, List<Discussion>> map = new HashMap<Integer, List<Discussion>>();
    public FileDiscussionDao() throws IOException {
        loadFile();
        loadJSONFile();
    }

    private void loadFile() throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(Config.DISCUSS_PATH), "GBK"));
        List<String> tokens = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        while (true){
            String line = br.readLine();
            if (cnt++ % 100 == 0)
                System.out.println(cnt);
            if (line != null)
                for (int i = 0; i < line.length(); i ++) {
                    char c = line.charAt(i);
                    if (c == 'Б')  {
                        tokens.add(sb.toString());
                        sb = new StringBuilder();
                    } else {
                        sb.append(c);
                    }
                }

            try {
                if (tokens.size() >= 33) {
                    Discussion discussion = new Discussion(Integer.parseInt(tokens.get(0)), Integer.parseInt(tokens.get(1)), Integer.parseInt(tokens.get(2)), Integer.parseInt(tokens.get(3)), tokens.get(7), tokens.get(8),tokens.get(10));
                    if(discussion.topicId == 0)
                        discussion.topicId = discussion.id;
                    if (!map.containsKey(discussion.topicId))
                        map.put(discussion.topicId, new ArrayList<Discussion>());
                    map.get(discussion.topicId).add(discussion);
                    discussion.author = Integer.parseInt(tokens.get(15));
                    discussion.readCount = Integer.parseInt(tokens.get(12));

                    for (int i = 0; i < 33; i ++)
                        tokens.remove(0);
                }
            } catch (Exception e) {
                LOG.error(e);
                continue;
            } finally {

                if (line == null)
                    break;
            }

        }
    }

    private void loadJSONFile() throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(Config.JSON_DISCUSS_PATH), "GBK"));
        long preNdatetime = 0;
        int cnt = 0;
        while (true){
            String line = br.readLine();
            if (cnt++ % 100 == 0)
                System.out.println(cnt);

            try {
                if (line != null) {
                    JSONObject json = new JSONObject(line);
                    Discussion discussion = new Discussion(Integer.parseInt((String) json.get("ID")), Integer.parseInt((String) json.get("ParentID")),
                            Integer.parseInt((String) json.get("TopicSortID")), Integer.parseInt((String) json.get("BoardId")),
                            Common.decodeElin((String) json.get("Title")), Common.decodeElin((String)json.get("Content")),(String)json.get("ndatetime"));
                    if (discussion.date < preNdatetime)
                        continue;
                    preNdatetime = discussion.date;
                    if(discussion.topicId == 0)
                        discussion.topicId = discussion.id;
                    if (!map.containsKey(discussion.topicId))
                        map.put(discussion.topicId, new ArrayList<Discussion>());
                    map.get(discussion.topicId).add(discussion);
                    discussion.author = Integer.parseInt((String) json.get("UserID"));
                    discussion.readCount = Integer.parseInt((String) json.get("Hits"));
                }
            } catch (Exception e) {
                LOG.error(e);
                continue;
            } finally {

                if (line == null)
                    break;
            }

        }

    }




    public Set<Integer> getTopicIds() {
        return this.map.keySet();
    }
    @Override
    public List<Discussion> getDiscussion(int topicId) {
        List<Discussion> ret = this.map.get(topicId);
        if (ret != null) {
            Collections.sort(ret);
        }
        return ret;
    }
}

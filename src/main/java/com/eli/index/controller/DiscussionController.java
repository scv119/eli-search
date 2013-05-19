package com.eli.index.controller;

import com.eli.index.document.DiscussionDoc;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 8/13/12
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class DiscussionController {
    private static final Logger logger = Logger.getLogger(DiscussionController.class);
    private DiscussionDao discussionDao;

    public DiscussionController() {
        this.discussionDao = new FileDiscussionDao();
    }

    public List<DiscussionDoc> getNextDiscussionDocs() {
        List<DiscussionDoc> ret = new ArrayList<DiscussionDoc>();
        List<Discussion> ids = this.discussionDao.getNextDiscussion();
        for (int i = 0; i * 20 < ids.size(); i ++ ) {
            for (int j = 0; j < 20 && i * 20 + j < ids.size(); j ++) {
                DiscussionDoc doc = new DiscussionDoc();
                Discussion dis= ids.get(i * 20 + j);
                doc.url = "http://new.elimautism.org/a/a.asp?ID="+ dis.topicId + "&Ap=" + i;
                doc.content = dis.content;
                ret.add(doc);
            }
        }
        return ret;
    }
}

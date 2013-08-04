package com.eli.index;

import com.eli.index.document.DiscussionDoc;
import com.eli.index.document.MemberDoc;
import com.eli.index.document.TopicDoc;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 8/24/12
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexConfig {
    public static final Class<? extends DocumentSupport> registeredClasses[] = new Class[] {
                DiscussionDoc.class, MemberDoc.class, TopicDoc.class};
}

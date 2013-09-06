package com.eli.index.controller;

import com.eli.index.document.TopicDoc;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: scv119
 * Date: 13-6-26
 * Time: 下午10:33
 * To change this template use File | Settings | File Templates.
 */
public class TopicController {
    private static final Logger logger = Logger.getLogger(TopicController.class);
    private static String[] CONTENT = {"以琳公告栏	",
            "本站的公告发布区。",
            "http://new.elimautism.org/b/b.asp?B=86",
            "站务讨论	",
            "若您对本网站的使用有疑问，对网站的建设有更好的建议，请来这里！",
            "http://new.elimautism.org/b/b.asp?B=72",
            "注意:初来者必读（常见问题）	",
            "初来乍到，先到这里看看，没准你的好多问题这里已经有答案！",
            "http://new.elimautism.org/b/b.asp?B=74",
            "帮我一把	",
            "是不是觉得很累？看看是否有人能帮助你，也看看是否你能帮到别人。让我们手拉手，一起走！",
            "http://new.elimautism.org/b/b.asp?B=43",
            "ABA行为问题管理专栏	",
            "我们一起来了解孩子想通过行为告诉我们什么信息,然后寻找适合的方法来帮助孩子建立良好的行为习惯。",
            "http://new.elimautism.org/b/b.asp?B=11",
            "家庭、婚姻、社会	",
            "父母是孩子的支柱,家庭是孩子的堡垒,社会是孩子遨游的海洋.",
            "http://new.elimautism.org/b/b.asp?B=20",
            "我家宝贝进步了！	",
            "将您孩子的点滴进步与大家分享，使我们的生活轻松一点，快乐一点，色彩亮丽一点！",
            "http://new.elimautism.org/b/b.asp?B=71",
            "教育训练	",
            "目前早期教育，行为和社交训练仍然是对我们孩子最有效的方法.",
            "http://new.elimautism.org/b/b.asp?B=5",
            "好文、好书、好网站	",
            "看到好的文章、好的教材或资料，好的网站，不要忘了跟大家分享哦！",
            "http://new.elimautism.org/b/b.asp?B=83",
            "信息交流栏	",
            "有关各种租房,转让,求购,交换,通知,布告等等",
            "http://new.elimautism.org/b/b.asp?B=237",
            "报名/评估/资料及相关问题解答	",
            "训练报名/评估/资料和相关的问题解答请来这里。",
            "http://new.elimautism.org/b/b.asp?B=94",
            "以琳教学研讨园地	",
            "慈绳爱索牵引你，教育方法成就你！（欢迎进入探讨，本栏只能回复主题）",
            "http://new.elimautism.org/b/b.asp?B=90",
            "以琳留言簿	",
            "您有什么话想对以琳说，或者想对以琳的老师说吗？",
            "http://new.elimautism.org/b/b.asp?B=78",
            "志愿者讨论区	",
            "讨论论坛事务，请志愿者入内...",
            "http://new.elimautism.org/b/b.asp?B=50",
    };

    public List<TopicDoc> getTopics() {
        List<TopicDoc> ret = new ArrayList<TopicDoc>();
        int idx = 0;
        while (idx < CONTENT.length) {
            TopicDoc doc = new TopicDoc();
            doc.setTitle(CONTENT[idx++]);
            doc.setContent(CONTENT[idx++]);
            doc.setUrl(CONTENT[idx++]);
            ret.add(doc);
        }
        return ret;
    }

}

package com.eli.index.document;

/**
 * Created with IntelliJ IDEA.
 * User: scv119
 * Date: 13-6-26
 * Time: 下午10:31
 * To change this template use File | Settings | File Templates.
 */

import com.eli.index.DocumentSupport;
import com.eli.index.IField;
import com.eli.index.IndexType;
import org.apache.lucene.document.Document;

import com.eli.index.DocumentSupport;
import com.eli.index.IndexType;
import com.eli.index.IField;
import org.apache.lucene.document.Document;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 8/10/12
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class TopicDoc extends DocumentSupport {

    public TopicDoc() {
    }

    public TopicDoc(Document doc) {
        super(doc);
    }

    @IField(indexTypes = {IndexType.None})
    public static final String type = "topic";

    @IField(indexTypes = {IndexType.NGRAM})
    public String content;

    @IField(indexTypes = {IndexType.None})
    public String url;

    @IField(indexTypes = {IndexType.NGRAM})
    public String title = "";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

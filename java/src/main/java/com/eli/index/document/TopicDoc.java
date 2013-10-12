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
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

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

    @Override
    public Query toDeleteQuery() {
        BooleanQuery ret = new BooleanQuery();
        Query typeQuery = new TermQuery(new Term("type.None", type));
        Query idQuery = new TermQuery(new Term("id.None", this.id));
        ret.add(typeQuery, BooleanClause.Occur.MUST);
        ret.add(idQuery, BooleanClause.Occur.MUST);
        return ret;
    }

    @IField(indexTypes = {IndexType.None})
    public static final String type = "topic";

    @IField(indexTypes = {IndexType.NGRAM})
    public String content;

    @IField(indexTypes = {IndexType.None})
    public String url;

    @IField(indexTypes = {IndexType.NGRAM}, boost = 5f)
    public String title = "";

    @IField(indexTypes = {IndexType.None})
    public String id = "";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

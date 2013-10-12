package com.eli.index.document;

import com.eli.index.DocumentSupport;
import com.eli.index.IField;
import com.eli.index.IndexType;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

/**
 * Created with IntelliJ IDEA.
 * User: scv119
 * Date: 13-6-26
 * Time: 下午11:55
 * To change this template use File | Settings | File Templates.
 */
public class MemberDoc extends DocumentSupport {
    public MemberDoc() {
    }

    public MemberDoc(Document doc) {
        super(doc);
    }

    @Override
    public Query toDeleteQuery() {
        BooleanQuery ret = new BooleanQuery();
        Query typeQuery = new TermQuery(new Term("type.NONE", type));
        Query idQuery = new TermQuery(new Term("id.NONE", this.id));
        ret.add(typeQuery, BooleanClause.Occur.MUST);
        ret.add(idQuery, BooleanClause.Occur.MUST);
        return ret;
    }

    @IField(indexTypes = {IndexType.None})
    public static final String type = "member";

    @IField(indexTypes = {IndexType.None}, boost = 5f)
    public String name = "";

    @IField(indexTypes = {IndexType.None})
    public String alias = "";

    @IField(indexTypes = {IndexType.None})
    public String url;

    @IField(indexTypes = {IndexType.None})
    public String avatar;

    @IField(indexTypes = {IndexType.None})
    public String id = "";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

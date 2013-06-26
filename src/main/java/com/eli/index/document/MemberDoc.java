package com.eli.index.document;

import com.eli.index.DocumentSupport;
import com.eli.index.IField;
import com.eli.index.IndexType;
import org.apache.lucene.document.Document;

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

    @IField(indexTypes = {IndexType.None})
    public static final String type = "member";

    @IField(indexTypes = {IndexType.None}, boost = 5f)
    public String name = "";

    @IField(indexTypes = {IndexType.None})
    public String alias = "";

    @IField(indexTypes = {IndexType.None})
    public String url;

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

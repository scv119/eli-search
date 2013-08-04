package com.eli.index.document;

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
public class DiscussionDoc extends DocumentSupport {

    public DiscussionDoc() {
    }

    public DiscussionDoc(Document doc) {
        super(doc);
    }

    @IField(indexTypes = {IndexType.None})
    public static final String type = "discussion";

    @IField(indexTypes = {IndexType.NGRAM})
    public String content;

    @IField(indexTypes = {IndexType.None})
    public String url;

    @IField(indexTypes = {IndexType.NGRAM})
    public String title = "";

    @IField(indexTypes = {IndexType.None})
    public int author;

    @IField(indexTypes = {IndexType.None})
    public long date;

    @IField(indexTypes = {IndexType.None})
    public int hits;

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


    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }
}

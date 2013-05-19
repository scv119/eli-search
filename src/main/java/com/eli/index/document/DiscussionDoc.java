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


    @IField(indexTypes = {IndexType.NGRAM})
    public String content;

    @IField(indexTypes = {IndexType.None})
    public String url;
}

package com.eli.index.manager;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.search.IndexSearcher;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 6/19/12
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultiNRTSearcherAgent {

    private IndexSearcher[] searchers;
    private IndexSearcher   searcher;
    private ZhihuNRTManager nrtManger;


    public MultiNRTSearcherAgent(ZhihuNRTManager nrtManger, IndexSearcher... searchers) throws IOException {
        this.nrtManger = nrtManger;
        this.searchers = searchers;
        IndexReader[] readers = new IndexReader[searchers.length];
        for(int i = 0 ; i < searchers.length ; i ++)
            readers[i] = searchers[i].getIndexReader();
        this.searcher = new IndexSearcher(new MultiReader(readers));
    }

    public ZhihuNRTManager getNrtManger(){
        return this.nrtManger;
    }



    public IndexSearcher[] getReleaseSearchers(){
        return this.searchers;
    }

    public IndexSearcher getSearcher(){
        return this.searcher;
    }

    public IndexReader getIndexReader(){
        return this.searcher.getIndexReader();
    }
}

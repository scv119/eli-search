package com.eli.web.action;



import com.eli.index.manager.MultiNRTSearcherAgent;
import com.eli.index.manager.ZhihuIndexManager;
import com.eli.web.BasicAction;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainSearch extends BasicAction {
    private static final Logger logger = Logger.getLogger(MainSearch.class);
    private static Analyzer analyzer= new CJKAnalyzer(Version.LUCENE_36);

    @Override
    protected void execute() throws IOException {
        String token = super.getParam("q", "");
        String avatar_url = null;
        int offset  = Integer.parseInt(super.getParam("offset", "0"));
        int limit  = Integer.parseInt(super.getParam("limit", "10"));
	int _type   = Integer.parseInt(super.getParam("type", "0"));

        super.put("query", token);
        super.put("offset", offset);
        super.put("limit", limit);
	super.put("type", _type);
        super.put("total", 0);
        super.put("page", 0);

        QueryParser qp = new QueryParser(Version.LUCENE_36, "content.NGRAM", analyzer);
        QueryParser qp1 = new QueryParser(Version.LUCENE_36, "title.NGRAM", analyzer);
        List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
        MultiNRTSearcherAgent agent = ZhihuIndexManager.INSTANCE.acquire();
        try{
            IndexSearcher searcher = agent.getSearcher();
            Query sub = qp.parse(token);
            Query sub1 = qp1.parse(token);
            Query sub2 = new TermQuery(new Term("name.None", token));

            BooleanQuery query = new BooleanQuery();
            if (_type == 0) {
                query.add(sub, BooleanClause.Occur.SHOULD);
                query.add(sub1, BooleanClause.Occur.SHOULD);
                query.add(sub2, BooleanClause.Occur.SHOULD);
            } else {
                Query sub3 = new TermQuery(new Term("type.None", _type == 1 ? "topic" : "member"));
                BooleanQuery sub4 = new BooleanQuery();

                sub4.add(sub, BooleanClause.Occur.SHOULD);
                sub4.add(sub1, BooleanClause.Occur.SHOULD);
                sub4.add(sub2, BooleanClause.Occur.SHOULD);

                query.add(sub3, BooleanClause.Occur.MUST);
                query.add(sub4, BooleanClause.Occur.MUST);
            }

            TopDocs hits = searcher.search(query, offset + limit);
            QueryScorer scorer = new QueryScorer(query);
            Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter("<span class=\"search-red\">", "</span>"), new SimpleHTMLEncoder(), scorer);
            highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer, 60));

            super.put("total", hits.totalHits);


            for (int i = offset; i < hits.scoreDocs.length && i < offset + limit; i++) {
                int docId = hits.scoreDocs[i].doc;
                Document doc = searcher.doc(docId);
                String content =  doc.get("content.NGRAM");
                String title =  doc.get("title.NGRAM");
                String type  = doc.get("type.None");

                if (type.equals("member")) {
                    content = "以琳用户";
                    title   = doc.get("name.None");
                    avatar_url = doc.get("avatar.None");
                    if (avatar_url == null || avatar_url.length() == 0)
                        avatar_url =  "http://new.elimautism.org/images/face/0003.gif";
                } else {
                    if (title == null)
                        title = "";
                    if  (content == null)
                        content = "";

                    String []highLight = getFragmentsWithHighlightedTerms(analyzer, query, "content.NGRAM", doc.get("content.NGRAM")
                                ,3, 3);
                    for (String s: highLight)
                        logger.info(s);

                    TokenStream stream = TokenSources.getAnyTokenStream(searcher.getIndexReader(), docId, "content.NGRAM", doc, analyzer );
                    String hContent = highlighter.getBestFragment(stream, content);
                    stream = TokenSources.getAnyTokenStream(searcher.getIndexReader(), docId, "title.NGRAM", doc, analyzer );
                    String hTitle = highlighter.getBestFragment(stream, title);
                    if (hTitle == null && title.length() == 0)
                        hTitle = "无标题";
                    else
                        hTitle = title.substring(0, Math.min(25, title.length()));
                    if  (hContent == null && (content == null || content.length() == 0))
                        hContent = "无内容";
                    else
                        hContent = content.substring(0, Math.min(80, content.length()));

                    if (type.equals("topic"))
                        hTitle = "论坛板块:" + hTitle;

                    content = hContent;
                    title   = hTitle;
                    avatar_url = null;

                }
                String url     =  doc.get("url.None");
                Map<String,String> map = new HashMap<String, String>();
                map.put("content", content);
                map.put("title", title);
                map.put("url", url);
                if (avatar_url != null)
                    map.put("avatar", avatar_url);
                ret.add(map);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            ZhihuIndexManager.INSTANCE.release(agent);
        }
        super.put("ret", ret);

    }

    public static String[] getFragmentsWithHighlightedTerms(Analyzer analyzer, Query query,
                                                     String fieldName, String fieldContents, int fragmentNumber, int fragmentSize) throws IOException, InvalidTokenOffsetsException {

        TokenStream stream = TokenSources.getTokenStream(fieldName, fieldContents, analyzer);
        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, fragmentSize);

        Highlighter highlighter = new Highlighter(scorer);
        highlighter.setTextFragmenter(fragmenter);
        highlighter.setMaxDocCharsToAnalyze(Integer.MAX_VALUE);

        String[] fragments = highlighter.getBestFragments(stream, fieldContents, fragmentNumber);

        return fragments;
    }

}

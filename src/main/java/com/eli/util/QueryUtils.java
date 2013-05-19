package com.eli.util;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.TopDocs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: sunan
 * Date: 12-6-25
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 */
public class QueryUtils {
    public static void main(String[] args) throws IOException, ParseException {


        while (true) {
            System.out.println("Enter query: ");
            BufferedReader lineOfText = new BufferedReader(new InputStreamReader(System.in, "gb2312"));
            String line = lineOfText.readLine();

            System.out.println(line);
            if (line == null || line.length() == -1) {
                break;
            }

            line = line.trim();
            if (line.length() == 0) {
                break;
            }

            TopDocs docs;
//            docs = ZhihuSearchFactory.search(line, ZhihuSearchFactory.TYPE_SEARCH_TOPIC);
            /*
            ScoreDoc[] hits = docs.scoreDocs;
            int count = 10;

            DecimalFormat scoreFormatter = new DecimalFormat("0.######");

            for (int i = 0; i < Math.min(count, hits.length); i++) {
                System.out.println("-----------");
                ScoreDoc hit = hits[i];
                System.out.println(hit);
            }
            */

        }

    }
}

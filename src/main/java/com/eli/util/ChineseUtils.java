package com.eli.util;

import com.spreada.utils.chinese.ZHConverter;
import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 6/18/12
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChineseUtils {
    private static final ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);

    public static List<String> getPinyin(char c){
        String[] list = PinyinHelper.toHanyuPinyinStringArray(c);
        Set<String> set = new HashSet<String>();
        if(list != null){
            for (String s:list){
                set.add(s.substring(0,s.length()-1));
            }
        }
        List<String> results = new ArrayList<String>();
        int i = 0 ;
        for(String s:set){
            if(s.contains("u:"))
            {
                results.add(s.replaceAll("u:","v"));
                results.add(s.replaceAll("u:","u"));
            }
            else
                results.add(s);
        }
        return results;
    }

    public static String simplify(String traditional){
        return converter.convert(traditional);
    }

    public static void main(String args[]){
        List<String> list =  getPinyin('吕');
        for(String s:list)
        System.out.println(s);

        list =  getPinyin('女');
        for(String s:list)
            System.out.println(s);

        list =  getPinyin('刘');
        for(String s:list)
            System.out.println(s);
    }

}

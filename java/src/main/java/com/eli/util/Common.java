package com.eli.util;

import com.google.gson.Gson;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.eli.util.BCConvert.SBC2DBC;
import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 6/15/12
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class Common {
    private static final Logger logger = Logger.getLogger(Common.class);
    private static final Gson gson = new Gson();
    public static final long MOD = 100000000000L;

    private Common() {
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static Object fromJson(String json, Class clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String getMachineName() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            logger.error("unable to get machine name", e);
        }
        return hostName;
    }

    public static long getTime() {
        Date date = new Date();
        return date.getTime();
    }

    public static String removeHtml(String name) {
        if (name == null)
            return "";
        String parStr = "<\\/?[^>]+>";
        Pattern pattern = Pattern.compile(parStr);
        Matcher matcher = pattern.matcher(name);
        String result = matcher.replaceAll("");
        result = result.replaceAll("\\&[a-zA-Z]{1,10}", "");
        return result;
    }

    public static String escapingQueryParserSpecialCharacters(String name) {
        if (name == null)
            return "";
        String parStr = "&&|\\|\\||[+-/!(){}\\[\\]^\"~*?:\\\\]";
        Pattern pattern = Pattern.compile(parStr);
        Matcher matcher = pattern.matcher(name);
        String result = matcher.replaceAll("");
        result = result.replaceAll("\\&[a-zA-Z]{1,10}", "");
        return result;
    }

    public static byte[] ObjectToByteArray(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            byte[] yourBytes = bos.toByteArray();
            return yourBytes;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
            try {
                bos.close();
            } catch (IOException e) {
            }
        }
        return null;
    }


    public static Object ByteArrayToObject(byte[] arrBytes) {
        ObjectInput in = null;
        ByteArrayInputStream bis = null;
        try {
            bis = new ByteArrayInputStream(arrBytes);
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return o;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
            }
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    static final String AVATAR_TOPIC = "e82bab09c";
    static final String AVATAR_MEMBER = "666b0abfc";

    public static String getAvatarPath(String avatar_path, String avatar_type, boolean is_topic) {
        String hashVal = avatar_path;

        if (avatar_path == null || avatar_path.length() == 0 ||
                avatar_path.length() != 9 || avatar_path.indexOf('#') >= 0)
            hashVal = is_topic ? AVATAR_TOPIC : AVATAR_MEMBER;

        int idx = (Integer.valueOf(hashVal.substring(5), 16) + 1) % Config.IMAGE_BASE_URL.length;
        String zhImg = Config.IMAGE_BASE_URL[idx];

        return "http://" + zhImg + "/" + hashVal.substring(0, 2) + "/" +
                hashVal.substring(2, 4) + "/" + hashVal + convertAvatarType(avatar_type) + ".jpg";
    }

    public static String getImagePath(String hashVal) {

        int idx = (Integer.valueOf(hashVal.substring(0, 1), 16) + 1) % Config.IMAGE_BASE_URL.length;
        String zhImg = Config.IMAGE_BASE_URL[idx];

        return "http://" + zhImg + "/" + hashVal.substring(0, 2) + "/" +
                hashVal.substring(2, 4) + "/" + hashVal + "_m.jpg";
    }

    public static String convertAvatarType(String avatar_type) {
        if (avatar_type.equals("custom"))
            return "_s";
        else if (avatar_type.equals("square"))
            return "_m";
        else if (avatar_type.equals("thumb"))
            return "_l";
        else if (avatar_type.equals("original"))
            return "";
        return avatar_type;
    }

    public static String html_escape(String query) {
        if (query == null)
            return null;
        query = escapeHtml4(query);
        if (query.indexOf("&middot;") != -1) {
            query = query.replaceAll("&middot;", "·");
        }
        if (query.indexOf("&mdash;") != -1) {
            query = query.replaceAll("&mdash;", "—");
        }
        return query;
    }



    public static int nowSecond() {
        Date date = new Date();
        return (int) (date.getTime() / 1000);
    }

    /**
     * 将字符串按照中文和其它字符切分成字符串序列
     * eg: facebook公司 -> facebook | 公司
     *
     * @param s 输入字符串
     * @return 字符串 Vector
     *         <p/>
     *         Wangxiao 2012/9/5
     */
    public static List<String> splitToChineseAndOther(String s) {
        String temp = "";
        int preType = 0;

        List<String> result = new ArrayList<String>();

        if (s == null)
            return result;

        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (i == 0) {
                int type = Character.getType(c);
                if (type == Character.OTHER_LETTER) {
                    preType = 1;
                } else {
                    preType = 0;
                }
                temp += c;
            } else {
                int type = Character.getType(c);
                if (type == Character.OTHER_LETTER) {
                    type = 1;
                } else {
                    type = 0;
                }
                if (type == preType) {
                    temp += c;
                } else {
                    String arr[] = temp.split(" ");
                    for (String ss : arr)
                        if (ss.length() > 0)
                            result.add(ss);
                    temp = "";
                    temp += c;
                    preType = type;
                }
            }
            if (i == s.length() - 1) {
                String arr[] = temp.split(" ");
                for (String ss : arr)
                    if (ss.length() > 0)
                        result.add(ss);
            }
        }
        return result;
    }



    public static String parseString(String from, boolean removeHtml, boolean transferTradition) {
        return parseString(from, removeHtml, transferTradition, true);
    }

    public static String parseString(String from, boolean removeHtml, boolean transferTradition, boolean transferLowerCase) {
        if (removeHtml)
            from = Common.removeHtml(from);
        if (transferTradition)
            from = ChineseUtils.simplify(from);
        if (transferLowerCase)
            from = from.toLowerCase();
        from = SBC2DBC(from);
        return from;
    }


    private static final String REG_STR = "(\\+|-|\\&\\&|\\|\\||!|\\(|\\)|\\[|\\]|\\{|\\}|\\^|\"|\\~|\\*|\\?|:|\\\\)";

    public static String escapeQuery4QueryParser(String query) {
        if (query != null)
            query = query.replaceAll(REG_STR, "\\\\$1");
        return query;
    }

    private static final String REG_STR_FOR_TOPIC = "[-《》·.「」:+!@()#——]";

    public static String escapeQuery4TopicSearch(String query) {
        if (query != null)
            query = query.replaceAll(REG_STR_FOR_TOPIC, " ");
        return query.trim();
    }


    static BASE64Decoder decoder = new BASE64Decoder();
    public static String decode(String base64) {
        String ret = "";
        try {
            byte[] decodeBytes = decoder.decodeBuffer(base64);
            ret = new String(decodeBytes, Charset.forName("gbk"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return ret;
        }
        return ret;
    }

    public static String decodeElin(String base64) {
        base64 = base64.replaceAll("%", "\r");
        return decode(base64);
    }

    public static void main(String args[]) {
//        String ret = decodeCookie("q_c0", "YjM2ZTdjN2RjMjkwNzgzZjdmNTBlNjA2ZTZiOTRmNTd8R3JsTmNIOVhLVGxJWU9oRQ==|" +
//                "1345879337|ab2db45bf03e2e08f9e06a6215930b2bce7a4b8a");
//
//        assert (ret != null);

        String s = "KFC你好";
        System.out.println(s.toLowerCase());

        s = "facebook你好  asdfa fuck hello c++你们1好asdf12么大家";
        List<String> list = splitToChineseAndOther(s);
        for (String ss : list) {
            System.out.println(ss);
        }

        System.out.println(decodeElin("0tTPwtL908PXqLzStcS21Luwo7oNCsj91MK35zqyu8Tct9bA4LXE19Sx1daiKFBERKGqTk9T%KcrH1PXR+bXE0rvW1rHtz9Y/DQqhoaGh197QobH4OrHIyOfSu7j20KG6oizL+zHL6tfz09LE%3L3QsNaw1sLowugsMcvqsOvSsrvh09DS4sq2tcS90LDWsNYs0rK74bHttO/X1Ly6tcTQ6Mfz%LKGwztLP68nPvdbN5qGxLLWry/u7+bG+yc+yu8TcvfjQ0NK7uPa72LrPtcS21LuwLMv71NrT%17b51LC74bOqtvm46Cy1q7TTwLSyu7vhw+jK9tPXtvnUsLeiyfq1xMrCx+mho8v70rK63MnZ%08PKs9a41rjO7yy74bK7u+HTw8qz1rjWuM7vLMrHztLDx9TnxtrJuLLp0ru49rrc1tjSqrXE%serXvKGju7nT0MDPyqa3tNOmy/u8x9LkwabHv7WrubXNqLLuLNK7uPbIy83moaPO0sPHvq25%/TO49tTCssXE3Mi31e/L+8rHUEREoapOT1Ohow0KyP3UwrfnOsT61PXR+b+0tP2hsNfUsdXW%orK7v8nWztP6obHV4tK7udu14z8NCqGhoaHX3tChsfg6ueO3urXE19Sx1dai09C63Lbgt9bA%4CzH4dDNt8e15NDN0NS1xNfUsdXWorK7tObU2tbOsrvWztP6tcTOysziLMv7w8exvsntvs3U%2r2hyKvIy7Wx1tAs1rvKx8v7w8e1xNDQzqrT0NCpudbS7CyyosfStNPX1LHV1qK1xMj9zqzR%0L6/wLS/tCzL5tfFyrG85LXEseS7ryzSu9Cpt8e15NDN0NTX1LHV1qLIy8q/tcS51tLs0NDO%qrvhy+nGrLuvLNPQ0Km51tLs0NDOqsO709DByyzT0NCpu7mxo8H018Whow=="));

        System.out.println("asdf-asdfasdf".replaceAll("-", " "));

    }

}

package com.eli.util;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 9/12/12
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimeUtil {
    public static Date getDateTime() {
        return new Date();
    }

    public static long getLongTime() {
        return getDateTime().getTime();
    }
}

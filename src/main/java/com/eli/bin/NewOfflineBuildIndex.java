package com.eli.bin;

import com.eli.index.manager.ZhihuNRTManager;
import com.eli.index.offline.BuildIndex;
/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 7/23/12
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewOfflineBuildIndex {
    public static void main(String args[]){
        BuildIndex.start();
        ZhihuNRTManager.stopPeriodRefresh();
    }
}

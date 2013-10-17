package com.eli.web.action;

import com.eli.index.manager.ZhihuIndexManager;
import com.eli.index.offline.BuildIndex;
import com.eli.web.BasicAction;
import com.eli.web.Handler;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 7/16/12
 * Time: 8:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdminAction extends BasicAction {

    @Override
    protected void execute() {
        String sHeat = null;
        String reload;
        boolean isRebuildIndex = false;

        if ((super.getParam("reload", "")).equals("true")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BuildIndex.start();
                }
            }).start();
        }

        if ((super.getParam("commit", "")).equals("true")) {
            ZhihuIndexManager.INSTANCE.finishBuild();
        }


        isRebuildIndex = false;

        super.put("rebuildIndex", isRebuildIndex + "");
        super.put("currentVersion", ZhihuIndexManager.INSTANCE.currentIndexVersion() + "");
        super.put("futureVersion", "no");

        super.put("qps", Handler.getQPS() + "");

    }
}
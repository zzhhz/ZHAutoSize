package com.zzh.lib.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.zzh.lib.core.utils.HAppBackgroundListener;
import com.zzh.lib.core.utils.HAppManager;

public class HLibrary {
    private static HLibrary sInstance;
    private Context mContext;

    private HLibrary() {
    }

    public static HLibrary getInstance() {
        if (sInstance == null) {
            synchronized (HLibrary.class) {
                if (sInstance == null)
                    sInstance = new HLibrary();
            }
        }
        return sInstance;
    }

    public Context getContext() {
        return mContext;
    }

    public synchronized void init(Application application) {
        if (application == null) {
            throw new NullPointerException("application is null, please check invoke init method");
        }

        if (mContext == null) {
            mContext = application;
            HAppManager.getInstance().init(application);
            HAppBackgroundListener.getInstance().init(application);
        }
    }

    /**
     * 获取最后一个activity
     *
     * @return
     */
    public static Activity getLastActivity() {
        return HAppManager.getInstance().getLastActivity();
    }
}

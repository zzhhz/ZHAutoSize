package com.zzh.lib.core.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HAppManager {
    private static HAppManager sInstance;

    private Application mApplication;
    private final List<Activity> mActivityHolder = new CopyOnWriteArrayList<>();

    private boolean mIsDebug;

    private HAppManager() {
    }

    public static HAppManager getInstance() {
        if (sInstance == null) {
            synchronized (HAppManager.class) {
                if (sInstance == null)
                    sInstance = new HAppManager();
            }
        }
        return sInstance;
    }

    public void setDebug(boolean debug) {
        mIsDebug = debug;
    }

    public synchronized void init(Application application) {
        if (mApplication == null) {
            mApplication = application;
            mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        }
    }

    private final Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
            final int index = mActivityHolder.indexOf(activity);
            if (index < 0)
                return;

            final int size = mActivityHolder.size();
            if (size <= 1)
                return;

            if (index != (size - 1)) {
                if (mIsDebug)
                    Log.e(HAppManager.class.getSimpleName(), "start order activity " + activity + " old index " + index);

                removeActivity(activity);
                addActivity(activity);

                if (mIsDebug)
                    Log.e(HAppManager.class.getSimpleName(), "end order activity " + activity + " new index " + mActivityHolder.indexOf(activity));
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            removeActivity(activity);
        }
    };

    private String getCurrentStack() {
        Object[] arrActivity = mActivityHolder.toArray();
        if (arrActivity != null) {
            return Arrays.toString(arrActivity);
        } else {
            return "";
        }
    }

    /**
     * 添加对象
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityHolder.contains(activity))
            return;

        mActivityHolder.add(activity);

        if (mIsDebug) {
            Log.i(HAppManager.class.getSimpleName(), "+++++ " + activity + " " + mActivityHolder.size()
                    + "\r\n" + getCurrentStack());
        }
    }

    /**
     * 移除对象
     *
     * @param activity
     */
    private void removeActivity(Activity activity) {
        if (mActivityHolder.remove(activity)) {
            if (mIsDebug) {
                Log.e(HAppManager.class.getSimpleName(), "----- " + activity + " " + mActivityHolder.size()
                        + "\r\n" + getCurrentStack());
            }
        }
    }

    /**
     * 返回栈中保存的对象个数
     *
     * @return
     */
    public int size() {
        return mActivityHolder.size();
    }

    /**
     * 返回栈中指定位置的对象
     *
     * @param index
     * @return
     */
    public Activity getActivity(int index) {
        try {
            return mActivityHolder.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回栈中最后一个对象
     *
     * @return
     */
    public Activity getLastActivity() {
        return getActivity(mActivityHolder.size() - 1);
    }

    /**
     * 是否包含指定对象
     *
     * @param activity
     * @return
     */
    public boolean containsActivity(Activity activity) {
        return mActivityHolder.contains(activity);
    }

    /**
     * 返回栈中指定类型的所有对象
     *
     * @param clazz
     * @return
     */
    public List<Activity> getActivity(Class<? extends Activity> clazz) {
        final List<Activity> list = new ArrayList<>(1);
        for (Activity item : mActivityHolder) {
            if (item.getClass() == clazz)
                list.add(item);
        }
        return list;
    }

    /**
     * 返回栈中指定类型的第一个对象
     *
     * @param clazz
     * @return
     */
    public Activity getFirstActivity(Class<? extends Activity> clazz) {
        for (Activity item : mActivityHolder) {
            if (item.getClass() == clazz)
                return item;
        }
        return null;
    }

    /**
     * 栈中是否包含指定类型的对象
     *
     * @param clazz
     * @return
     */
    public boolean containsActivity(Class<? extends Activity> clazz) {
        return getFirstActivity(clazz) != null;
    }

    /**
     * 结束栈中指定类型的对象
     *
     * @param clazz
     */
    public void finishActivity(Class<? extends Activity> clazz) {
        final List<Activity> list = getActivity(clazz);
        for (Activity item : list) {
            item.finish();
        }
    }

    /**
     * 结束栈中除了activity外的所有对象
     *
     * @param activity
     */
    public void finishActivityExcept(Activity activity) {
        for (Activity item : mActivityHolder) {
            if (item != activity)
                item.finish();
        }
    }

    /**
     * 结束栈中除了指定类型外的所有对象
     *
     * @param clazz
     */
    public void finishActivityExcept(Class<? extends Activity> clazz) {
        for (Activity item : mActivityHolder) {
            if (item.getClass() != clazz)
                item.finish();
        }
    }

    /**
     * 结束栈中除了activity外的所有activity类型的对象
     *
     * @param activity
     */
    public void finishSameClassActivityExcept(Activity activity) {
        for (Activity item : mActivityHolder) {
            if (item.getClass() == activity.getClass()) {
                if (item != activity)
                    item.finish();
            }
        }
    }

    /**
     * 结束所有对象
     */
    public void finishAllActivity() {
        for (Activity item : mActivityHolder) {
            item.finish();
        }
    }
}
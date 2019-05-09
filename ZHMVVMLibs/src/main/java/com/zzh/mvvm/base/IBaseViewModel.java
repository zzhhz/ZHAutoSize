package com.zzh.mvvm.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by Administrator.
 *
 * @date: 2019/1/17
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: ZHLiveApp
 * @since 1.0
 */
public interface IBaseViewModel extends LifecycleObserver {
    /**
     * @param owner
     * @param event
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny(LifecycleOwner owner, Lifecycle.Event event);

    /**
     *
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    /**
     *
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    /**
     * 注册RxBus
     */
    void registerRxBus();

    /**
     * 移除RxBus
     */
    void removeRxBus();
}

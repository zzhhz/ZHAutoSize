package com.zzh.web;

import android.app.Activity;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.zzh.lib.core.HLibrary;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2018/9/4
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: NetworkScheduler.java 简化线程调度写作方式
 */
public class NetworkScheduler {
    public static <T> ObservableTransformer<T, T> compose() {
        final Activity lastActivity = HLibrary.getLastActivity();
        if (lastActivity instanceof RxAppCompatActivity) {
            return upstream -> upstream.subscribeOn(Schedulers.io()).compose(((RxAppCompatActivity) lastActivity).bindToLifecycle()).observeOn(AndroidSchedulers.mainThread());
        } else {
            return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    }
}

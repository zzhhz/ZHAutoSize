package com.zzh.mvvm.base;

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
public interface IBaseView {
    /**
     * 初始化界面传递参数
     */
    void initParams();
    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();
}

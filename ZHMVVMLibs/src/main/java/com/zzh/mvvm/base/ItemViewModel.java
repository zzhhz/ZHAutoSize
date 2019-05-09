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
public class ItemViewModel<VM extends BaseViewModel> {
    protected VM vm;

    public ItemViewModel(VM vm) {
        this.vm = vm;
    }
}

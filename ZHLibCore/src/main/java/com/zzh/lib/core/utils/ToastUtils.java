package com.zzh.lib.core.utils;

import android.widget.Toast;

import com.zzh.lib.core.HLibrary;

/**
 * Created by Administrator.
 *
 * @date: 2019/8/15
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: ZHAutoSize.git
 * @since 1.0
 */
public class ToastUtils {
    /**
     * @param msg 提示的文字
     */
    public static void show(String msg) {
        Toast.makeText(HLibrary.getLastActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}

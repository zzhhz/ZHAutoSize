package com.zzh.mvvm.ui.dialog;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zzh.mvvm.R;

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
public class LoadingDialog extends Dialog {
    TextView tv_msg;
    ImageView iv_loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.zh_loading_dialog);
        setContentView(R.layout.dialog_loading);
        tv_msg = findViewById(R.id.tv_msg);
        iv_loading = findViewById(R.id.iv_loading);
    }

    /**
     * @param msg
     */
    public void setTipMsg(String msg) {
        if (tv_msg != null && !TextUtils.isEmpty(msg)) {
            tv_msg.setText(msg);
        }
    }

    public void show(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tv_msg.setText(msg);
        } else {
            tv_msg.setText("加载中...");
        }
        show();
        initAnim();
    }

    private void initAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(iv_loading, "rotation", 0f, 359f);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.setDuration(1000);
        anim.start();
    }
}

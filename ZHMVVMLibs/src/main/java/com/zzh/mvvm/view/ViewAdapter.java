package com.zzh.mvvm.view;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding3.view.RxView;
import com.zzh.mvvm.R;
import com.zzh.mvvm.bus.binding.command.BindingCommand;
import com.zzh.mvvm.utils.LayoutManagers;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * Created by goldze on 2017/6/16.
 */

public class ViewAdapter {
    //防重复点击间隔(秒)
    public static final int CLICK_INTERVAL = 1;

    /**
     * requireAll 是意思是是否需要绑定全部参数, false为否
     * View的onClick事件绑定
     * onClickCommand 绑定的命令,
     * isThrottleFirst 是否开启防止过快点击
     */
    @BindingAdapter(value = {"onClickCommand", "isThrottleFirst"}, requireAll = false)
    public static void onClickCommand(View view, final BindingCommand clickCommand, final boolean isThrottleFirst) {
        if (isThrottleFirst) {
            RxView.clicks(view)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            if (clickCommand != null) {
                                clickCommand.execute();
                            }
                        }
                    });
        } else {
            RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            if (clickCommand != null) {
                                clickCommand.execute();
                            }
                        }
                    });
        }
    }

    /**
     * view的onLongClick事件绑定
     */
    @BindingAdapter(value = {"onLongClickCommand"}, requireAll = false)
    public static void onLongClickCommand(View view, final BindingCommand clickCommand) {
        RxView.longClicks(view)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        if (clickCommand != null) {
                            clickCommand.execute();
                        }
                    }
                });
    }

    /**
     * 回调控件本身
     *
     * @param currentView
     * @param bindingCommand
     */
    @BindingAdapter(value = {"currentView"}, requireAll = false)
    public static void replyCurrentView(View currentView, BindingCommand bindingCommand) {
        if (bindingCommand != null) {
            bindingCommand.execute(currentView);
        }
    }

    /**
     * view是否需要获取焦点
     */
    @BindingAdapter({"requestFocus"})
    public static void requestFocusCommand(View view, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        } else {
            view.clearFocus();
        }
    }

    /**
     * view的焦点发生变化的事件绑定
     */
    @BindingAdapter({"onFocusChangeCommand"})
    public static void onFocusChangeCommand(View view, final BindingCommand<Boolean> onFocusChangeCommand) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (onFocusChangeCommand != null) {
                    onFocusChangeCommand.execute(hasFocus);
                }
            }
        });
    }

//    @BindingAdapter({"onTouchCommand"})
//    public static void onTouchCommand(View view, final ResponseCommand<MotionEvent, Boolean> onTouchCommand) {
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (onTouchCommand != null) {
//                    return onTouchCommand.execute(event);
//                }
//                return false;
//            }
//        });
//    }

    @BindingAdapter({"onChargeType"})
    public static void chargeType(TextView view, int type) {
        if (view == null) {
            return;
        }
        String payType = "现金";
        switch (type) {
            case 1:
                payType = "现金支付";
                break;
            case 2:
                payType = "支付宝支付";
                break;
            case 3:
                payType = "微信支付";

                break;
            case 4:
                payType = "银行卡转账";
                break;
            default:
                payType = "其他支付";
                break;
        }
        view.setText(payType);
    }

    @BindingAdapter({"onChargeTextColor"})
    public static void chargeTextColor(TextView view, double money) {
        if (view == null) {
            return;
        }
        if (money > 0) {
            view.setTextColor(Color.parseColor("#3a8bf5"));
        } else {
            view.setTextColor(Color.parseColor("#343434"));
        }
    }

    @BindingAdapter(value = {"setLayoutManager", "setRecyclerAdapter"}, requireAll = false)
    public static void recyclerSet(RecyclerView view, LayoutManagers.LayoutManagerFactory manager, RecyclerView.Adapter adapter) {
        view.setLayoutManager(manager.create(view));
        view.setAdapter(adapter);
    }


    @BindingAdapter({"loadRichText"})
    public static void loadRichText(WebView view, String richText) {
        view.loadDataWithBaseURL(null, richText, "text/html", "utf-8", null);
    }
}

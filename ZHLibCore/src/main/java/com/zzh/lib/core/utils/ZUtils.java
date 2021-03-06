package com.zzh.lib.core.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.core.graphics.drawable.DrawableCompat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZZH on 17/2/4.
 *
 * @Date: 17/2/4
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 对一些工具的封装<br />
 * 1.单位转换<br/>
 * 2.日期格式化<br/>
 * 3.网络判断<br/>
 * 4.屏幕宽高<br/>
 */

public class ZUtils {
    /**
     * Log输出标识
     **/
    private static final String TAG = "--ZUtils--";
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     */
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DF_CN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式：yyyy/MM/dd
     */
    public static final String DF_YYYY_MM_DD_SEC = "yyyy/MM/dd";

    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";

    /**
     * 日期格式：HH:mm
     */
    public static final String DF_HH_MM = "HH:mm";

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年
    public final static long FOUR_HOUR = 14400000;

    /**
     * @param ctx 上下文
     * @param px  像素
     * @return 返回值单位是dp
     */
    public static int px2dp(Context ctx, float px) {
        float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * @param ctx 上下文
     * @param dp  dp值
     * @return 返回像素值
     */
    public static int dp2px(Context ctx, float dp) {
        float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * @param ctx 上下文
     * @param px  像素
     * @return 返回值单位是dp
     */
    public static int px2sp(Context ctx, float px) {
        final float fontScale = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    /**
     * @param ctx 上下文
     * @param sp  字体大小
     * @return 返回字体像素值
     */
    public static int sp2px(Context ctx, float sp) {
        final float fontScale = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (fontScale * sp + 0.5);
    }

    /**
     * 获取屏幕的宽度
     *
     * @param ctx 上下文
     * @return 屏幕宽度
     */
    public static int getDisplayWidth(Context ctx) {
        DisplayMetrics dm = ctx.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取手机屏幕真实的高度。一般获取手机屏幕的高度是真实手机长度减去状态栏的高度。
     * getRealMetrics 这个方法是API 17之后才有的，之前的使用@hide 来标记的，并不对外开放API，所以API 小于 17使用反射的方法取得屏幕的真实高度
     *
     * @param ctx 上下文
     * @return 屏幕高度
     */
    public static int getDisplayHeight(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealMetrics(dm);
        } else {
            try {
                Class<?> clz = Class.forName("android.view.Display");
                Method method = clz.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(wm.getDefaultDisplay(), dm);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return dm.heightPixels;//获取宽度
    }

    /**
     * 判断手机号是否合法
     *
     * @param mobiles 手机号
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((14[5-7])|(17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9])|(19[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 对字符串复制
     *
     * @param act
     * @param str
     */
    public static void copyString(Activity act, String str) {
        ClipboardManager clipboardManager = (ClipboardManager) act.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("tmp", str);
        clipboardManager.setPrimaryClip(data);
    }

    /**
     * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
     *
     * @param date
     * @return
     */
    public static String formatTimeFriendly(Date date) {
        if (date == null) {
            return null;
        }
        long diff = System.currentTimeMillis() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 将日期字符串转成日期
     *
     * @param strDate 字符串日期
     * @param format  格式化的样式
     * @return java.util.date 日期类型
     */
    public static Date parseDate(String strDate, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date returnDate = null;
        try {
            returnDate = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param date 日期
     * @return
     */
    public static String formatDateTime(long date) {
        return formatDateTime(date, DF_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 将日期以给定的格式格式化
     *
     * @param time 日期
     * @return
     */
    public static String formatDateTime(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }



    /**
     * 是否是pad
     *
     * @param ctx
     * @return
     */
    public static boolean isPad(Context ctx) {
        return (ctx.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 是否可以打电话
     *
     * @param activity
     * @return
     */
    public static boolean isCall(Activity activity) {
        TelephonyManager telephony = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断网络是否可用
     *
     * @param ctx
     * @return true 网络可用。
     */
    public static boolean isNetWork(Context ctx) {
        if (ctx != null) {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                return ni.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断wifi是否可用
     *
     * @param mContext
     * @return true 网络可用。
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 判断是哪一种网络
     *
     * @param ctx
     * @return 返回网络类型
     */
    public static NetType getNetType(Context ctx) {
        if (!isNetWork(ctx)) {
            return NetType.NONETWORK;//不可用网络
        }
        NetType type = NetType.NONE;
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
                type = NetType.WIFI;
            } else if (ni.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = ni.getSubtypeName();

                int networkType = ni.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        type = NetType.G2;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD: //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP: //api<13 : replace by 15
                        type = NetType.G3;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:  //api<11 : replace by 13
                        type = NetType.G4;
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            type = NetType.G3;
                        } else {
                            type = NetType.GPRS;
                        }

                        break;
                }

            }
        }
        return type;
    }

    /**
     * 给 View 背景着色
     *
     * @param drawable
     * @param colors
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /**
     * @param drawable
     * @param colors
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, @ColorInt int colors) {
        return tintDrawable(drawable, ColorStateList.valueOf(colors));
    }

    /**
     * @param drawable
     * @param colors
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, String colors) {
        return tintDrawable(drawable, ColorStateList.valueOf(Color.parseColor(colors)));
    }

    public enum NetType {
        G2,//2G
        G3,//3G
        G4,//4G
        WIFI,//无线网
        GPRS,//移动网络
        NONETWORK,//无网络连接
        NONE//未知网络
    }
}

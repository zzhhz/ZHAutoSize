package com.zzh.lib.core;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

/**
 * Created by ZZH on 2019-12-11.
 *
 * @Date: 2019-12-11
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description:
 */
public class HStringSpannable extends SpannableStringBuilder {
    public SpannableStringBuilder append(CharSequence text, int textColor) {
        ForegroundColorSpan textSpan = new ForegroundColorSpan(textColor);
        appendSpan(textSpan, text);
        return this;
    }

    public static final int DEFAULT_FLAG = SPAN_EXCLUSIVE_EXCLUSIVE;

    public HStringSpannable() {
    }

    public void appendSpan(Object span, CharSequence key) {
        this.appendSpan(span, key, DEFAULT_FLAG);
    }

    public void appendSpan(Object span, CharSequence key, int flags) {
        if (span != null && !TextUtils.isEmpty(key)) {
            this.append(key);
            int end = this.length();
            int start = end - key.length();
            this.setSpan(span, start, end, flags);
        }

    }

    public void addSpan(Object spanOld, Object spanNew) {
        this.addSpan(spanOld, spanNew, 33);
    }

    public void addSpan(Object spanOld, Object spanNew, int flags) {
        if (spanNew != null && spanOld != null) {
            int end = this.getSpanEnd(spanOld);
            int start = this.getSpanStart(spanOld);
            this.setSpan(spanNew, start, end, flags);
        }

    }

    public void setSpan(Object what, int start, int end) {
        this.setSpan(what, start, end, 33);
    }
}

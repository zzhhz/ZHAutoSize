package com.zzh.mvvm.base;

/**
 * Created by Administrator.
 *
 * @date: 2019/1/22
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: ReformWaterPrice
 * @since 1.0
 */
public class RespModel<T> {
    private int code;
    private String message;
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

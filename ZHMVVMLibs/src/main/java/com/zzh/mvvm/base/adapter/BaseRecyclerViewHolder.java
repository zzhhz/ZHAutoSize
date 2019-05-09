package com.zzh.mvvm.base.adapter;


import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Administrator.
 *
 * @date: 2019/4/22
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: ReformWaterPrice
 * @since 1.0
 */
public class BaseRecyclerViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private T dataBinding;

    public BaseRecyclerViewHolder(@NonNull T item) {
        super(item.getRoot());
        dataBinding = item;
    }

    public T getDataBinding() {
        return dataBinding;
    }
}

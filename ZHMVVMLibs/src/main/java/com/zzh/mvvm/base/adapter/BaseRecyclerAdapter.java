package com.zzh.mvvm.base.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator.
 *
 * @date: 2019/4/22
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description:
 * @since 1.0
 */
public abstract class BaseRecyclerAdapter<T, V extends ViewDataBinding> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    private List<T> mDataList;

    public BaseRecyclerAdapter() {
        mDataList = new ArrayList<>();
    }

    public void addAll(List<T> mDataList) {
        int start = mDataList.size();
        int end = mDataList == null ? 0 : mDataList.size();
        this.mDataList.addAll(mDataList);
        notifyItemRangeInserted(start, end);
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        V v = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), setItemLayoutRes(viewType), parent, false);
        return new BaseRecyclerViewHolder(v);
    }

    /**
     * 布局Item
     *
     * @param viewType
     * @return
     */
    public abstract int setItemLayoutRes(int viewType);

    /**
     * 设置 variable id
     *
     * @return
     */
    public abstract int setItemVariableId();

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        holder.getDataBinding().setVariable(setItemVariableId(), mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}

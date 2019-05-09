package com.zzh.mvvm.utils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Administrator.
 *
 * @date: 2019/5/9
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: ZHAutoSize
 * @since 1.0
 */
public class LayoutManagers {
    public static LayoutManagerFactory linearHorizontal() {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView) {
                return new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, true);
            }
        };
    }

    public static LayoutManagerFactory linearVertical() {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView) {
                return new LinearLayoutManager(recyclerView.getContext());
            }
        };
    }

    public interface LayoutManagerFactory {
        RecyclerView.LayoutManager create(RecyclerView recyclerView);
    }
}

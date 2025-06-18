package com.simplecityapps.recycler_adapter.model;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public interface ViewModel<V extends RecyclerView.ViewHolder> extends ContentsComparator {

    int getViewType();

    void bindView(V holder);

    void bindView(V holder, int position, List payloads);

    VH createViewHolder(ViewGroup parent);

    int getSpanSize(int spanCount);
}
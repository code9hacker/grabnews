package com.bitfault.grabnews.common.ui.recyclerView;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bitfault.grabnews.common.model.BaseRecyclerItemViewData;

import java.util.ArrayList;
import java.util.List;

/**
 * Base generic class of a recyclerview adapter
 */
public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    private List<BaseRecyclerItemViewData> viewDataList = new ArrayList<>();

    public BaseRecyclerViewAdapter(List<BaseRecyclerItemViewData> viewDataList) {
        this.viewDataList.addAll(viewDataList);
    }

    /**
     * Refreshes recycler view contents
     * @param viewDataList new data list
     */
    public void updateDataList(List<BaseRecyclerItemViewData> viewDataList) {
        this.viewDataList.clear();
        this.viewDataList.addAll(viewDataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BaseRecyclerViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), i, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder baseRecyclerViewHolder, int i) {
        baseRecyclerViewHolder.bindDataToViewHolder(viewDataList.get(i));
    }

    @Override
    public int getItemCount() {
        return viewDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewDataList.get(position).getLayoutRes();
    }
}

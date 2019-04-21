package com.bitfault.grabnews.common.ui.recyclerView;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bitfault.grabnews.common.model.BaseRecyclerItemViewData;
import com.bitfault.grabnews.common.util.CollectionUtils;

import java.util.Map;

/**
 * Base generic recyclerview view holder which uses databinding
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    protected ViewDataBinding binding;
    protected View container;

    public BaseRecyclerViewHolder(@NonNull ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.container = binding.getRoot();
    }

    public View getContainer() {
        return container;
    }

    protected void bindDataToViewHolder(BaseRecyclerItemViewData data) {
        if (data == null || CollectionUtils.isNullEmpty(data.getBindingModelMap())) {
            return;
        }
        for (Map.Entry<Integer, Object> viewModel : data.getBindingModelMap().entrySet()) {
            binding.setVariable(viewModel.getKey(), viewModel.getValue());
        }
        binding.executePendingBindings();
    }

}

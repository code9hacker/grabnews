package com.bitfault.grabnews.screen.country;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitfault.grabnews.R;
import com.bitfault.grabnews.databinding.CountrySelectionLayoutBinding;

public class CountrySelectionFragment extends Fragment implements
        CountrySelectionViewModel.InteractionListener {

    private static final String LOG_TAG = "CountrySelectionFragment";
    private CountrySelectionViewModel viewModel;
    private InteractionListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof InteractionListener) {
            listener = (InteractionListener) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CountrySelectionLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.country_selection_layout, container, false);
        viewModel = ViewModelProviders.of(this, new ViewModelProvider.NewInstanceFactory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new CountrySelectionViewModel(CountrySelectionFragment.this);
            }
        }).get(CountrySelectionViewModel.class);
        getLifecycle().addObserver(viewModel);
        binding.setModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        viewModel.setUpUI();
    }

    @Override
    public void onSaveButtonClick(String code) {
        if (listener != null) {
            listener.onCountrySelected(code);
        }
    }

    public interface InteractionListener {
        void onCountrySelected(String code);
    }
}

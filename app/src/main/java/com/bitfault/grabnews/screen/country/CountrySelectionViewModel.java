package com.bitfault.grabnews.screen.country;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.bitfault.grabnews.BR;
import com.bitfault.grabnews.R;
import com.bitfault.grabnews.common.core.AppConfig;
import com.bitfault.grabnews.common.core.GrabNewsApplication;
import com.bitfault.grabnews.common.model.BaseRecyclerItemViewData;
import com.bitfault.grabnews.common.storage.SharedPreferenceHelper;
import com.bitfault.grabnews.common.util.CollectionUtils;
import com.bitfault.grabnews.pojo.config.Config;
import com.bitfault.grabnews.pojo.config.CountryInfoConfig;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CountrySelectionViewModel extends ViewModel implements LifecycleObserver,
        CountryItemViewModel.InteractionListener {

    public ObservableList<BaseRecyclerItemViewData> adapterList = new ObservableArrayList<>();
    private CountryItemViewModel selectedViewModel;
    private InteractionListener listener;

    @Inject
    AppConfig appConfig;

    @Inject
    SharedPreferenceHelper sharedPreferenceHelper;

    public CountrySelectionViewModel(InteractionListener listener) {
        this.listener = listener;
        GrabNewsApplication.applicationComponent.inject(this);
    }

    public void setUpUI() {
        addDataToAdapterList();
    }

    private void addDataToAdapterList() {
        Config config = appConfig.getConfig();
        if (config == null || config.getCountries() == null || CollectionUtils.isNullEmpty(config.getCountries().getCountryList())) {
            return;
        }
        String baseImageUrl = config.getCountries().getIconUrl();
        List<BaseRecyclerItemViewData> dataList = new ArrayList<>();
        for (CountryInfoConfig infoConfig : config.getCountries().getCountryList()) {
            CountryItemViewModel itemViewModel = getItemViewModel(baseImageUrl, infoConfig);
            BaseRecyclerItemViewData viewData = new BaseRecyclerItemViewData(R.layout.country_box_layout);
            viewData.addBindingViewModel(BR.model, itemViewModel);
            dataList.add(viewData);
        }

        adapterList.addAll(dataList);
    }

    private CountryItemViewModel getItemViewModel(String baseImageUrl, @NonNull CountryInfoConfig config) {
        String imageUrl = baseImageUrl != null ? baseImageUrl.replace("{CODE}", config.getCode()) : null;
        CountryItemViewModel itemViewModel = new CountryItemViewModel(imageUrl, config.getDisplayName(), config.getCode());
        itemViewModel.setListener(this);
        return itemViewModel;
    }

    public void onSaveButtonClick() {
        if (selectedViewModel == null) {
            Toast.makeText(GrabNewsApplication.mContext, "Please choose a country", Toast.LENGTH_SHORT).show();
        } else {
            sharedPreferenceHelper.putString(SharedPreferenceHelper.KEY_COUNTRY_CODE, selectedViewModel.getCode());
            listener.onSaveButtonClick(selectedViewModel.getCode());
        }
    }

    @Override
    public void onCountryItemClicked(@NonNull CountryItemViewModel itemViewModel) {
        if (selectedViewModel != null) {
            selectedViewModel.setShowCheck(false);
        }
        itemViewModel.setShowCheck(true);
        selectedViewModel = itemViewModel;
    }

    public interface InteractionListener {
        void onSaveButtonClick(String code);
    }
}

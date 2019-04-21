package com.bitfault.grabnews.screen.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bitfault.grabnews.R;
import com.bitfault.grabnews.common.model.event.EventType;
import com.bitfault.grabnews.common.model.event.LaunchActivityEvent;
import com.bitfault.grabnews.common.util.AppConstants;
import com.bitfault.grabnews.common.util.StringUtils;
import com.bitfault.grabnews.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private static final String LOG_TAG = "HomeActivity";
    private HomeActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        viewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        getLifecycle().addObserver(viewModel);
        binding.setModel(viewModel);
        viewModel.getEventLiveData().observe(this, baseEvent -> {
            switch (baseEvent.getType()) {
                case EventType.EVENT_LAUNCH_ACTIVITY:
                    LaunchActivityEvent launchActivityEvent = (LaunchActivityEvent) baseEvent;
                    Intent intent = new Intent(HomeActivity.this, launchActivityEvent.getActivityClass());
                    if (launchActivityEvent.getBundle() != null) {
                        intent.putExtras(launchActivityEvent.getBundle());
                    }
                    startActivity(intent);
                default:
                    break;
            }
        });
        String countryCode = AppConstants.DEFAULT_COUNTRY_CODE;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String code = getIntent().getExtras().getString(AppConstants.BUNDLE_KEY_COUNTRY_CODE);
            if (StringUtils.isNotNullEmpty(code)) {
                countryCode = code;
            }
        }
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel.setUpUI(countryCode);
    }

}

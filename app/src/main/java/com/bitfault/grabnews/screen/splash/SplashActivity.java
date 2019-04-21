package com.bitfault.grabnews.screen.splash;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bitfault.grabnews.R;
import com.bitfault.grabnews.common.model.event.AddFragmentEvent;
import com.bitfault.grabnews.common.model.event.BaseEvent;
import com.bitfault.grabnews.common.model.event.EventType;
import com.bitfault.grabnews.common.model.event.LaunchActivityEvent;
import com.bitfault.grabnews.databinding.ActivitySplashBinding;
import com.bitfault.grabnews.screen.country.CountrySelectionFragment;

public class SplashActivity extends AppCompatActivity implements
        CountrySelectionFragment.InteractionListener {

    private static final String LOG_TAG = "SplashActivity";
    private SplashActivityViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        viewModel = ViewModelProviders.of(this).get(SplashActivityViewModel.class);
        getLifecycle().addObserver(viewModel);
        viewModel.getEventLiveData().observe(this, eventObserver);
        binding.setModel(viewModel);
    }

    private Observer<BaseEvent> eventObserver = baseEvent -> {
        switch (baseEvent.getType()) {
            case EventType.EVENT_ADD_FRAGMENT:
                AddFragmentEvent addFragmentEvent = (AddFragmentEvent) baseEvent;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(addFragmentEvent.getContainerId(), addFragmentEvent.getFragment());
                if (addFragmentEvent.isAddToBackStack()) {
                    transaction.addToBackStack(addFragmentEvent.getTag());
                }
                transaction.commitAllowingStateLoss();
                break;
            case EventType.EVENT_LAUNCH_ACTIVITY:
                LaunchActivityEvent launchActivityEvent = (LaunchActivityEvent) baseEvent;
                Intent intent = new Intent(SplashActivity.this, launchActivityEvent.getActivityClass());
                if (launchActivityEvent.getBundle() != null) {
                    intent.putExtras(launchActivityEvent.getBundle());
                }
                startActivity(intent);
                finish();
            default:
                break;
        }
    };

    @Override
    public void onCountrySelected(String code) {
        viewModel.onCountrySelected(code);
    }
}

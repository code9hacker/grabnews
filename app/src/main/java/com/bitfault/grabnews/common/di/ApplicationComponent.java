package com.bitfault.grabnews.common.di;

import com.bitfault.grabnews.common.core.CommonApiManager;
import com.bitfault.grabnews.common.core.GrabNewsApplication;
import com.bitfault.grabnews.common.network.NetworkManager;
import com.bitfault.grabnews.screen.country.CountrySelectionViewModel;
import com.bitfault.grabnews.screen.home.HomeActivityViewModel;
import com.bitfault.grabnews.screen.splash.SplashActivityViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(GrabNewsApplication application);

    void inject(CountrySelectionViewModel viewModel);

    void inject(SplashActivityViewModel viewModel);

    void inject(NetworkManager networkManager);

    void inject(CommonApiManager apiManager);

    void inject(HomeActivityViewModel viewModel);

}

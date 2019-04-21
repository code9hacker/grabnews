package com.bitfault.grabnews.screen.home;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.bitfault.grabnews.BR;
import com.bitfault.grabnews.R;
import com.bitfault.grabnews.common.core.CommonApiManager;
import com.bitfault.grabnews.common.core.GrabNewsApplication;
import com.bitfault.grabnews.common.model.BaseRecyclerItemViewData;
import com.bitfault.grabnews.common.model.event.BaseEvent;
import com.bitfault.grabnews.common.model.event.LaunchActivityEvent;
import com.bitfault.grabnews.common.network.ResponseCacheManager;
import com.bitfault.grabnews.common.util.AppConstants;
import com.bitfault.grabnews.common.util.CollectionUtils;
import com.bitfault.grabnews.common.util.StringUtils;
import com.bitfault.grabnews.pojo.newsapi.NewsArticle;
import com.bitfault.grabnews.pojo.newsapi.TopHeadlinesResponse;
import com.bitfault.grabnews.screen.topHeadlines.NewsItemViewModel;
import com.bitfault.grabnews.screen.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class HomeActivityViewModel extends ViewModel implements LifecycleObserver,
        NewsItemViewModel.InteractionListener {

    private static final String LOG_TAG = "HomeActivityViewModel";
    private MutableLiveData<BaseEvent> eventLiveData = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ObservableList<BaseRecyclerItemViewData> adapterList = new ObservableArrayList<>();
    public ObservableBoolean showProgressBar = new ObservableBoolean(true);
    public ObservableBoolean showErrorView = new ObservableBoolean(false);

    private String countryCode;

    @Inject
    CommonApiManager apiManager;

    public HomeActivityViewModel() {
        GrabNewsApplication.applicationComponent.inject(this);
    }

    private List<NewsArticle> fetchNewsArticlesFromCache(String countryCode) {
        String cacheKey = AppConstants.TOP_HEADLINES_CACHE_KEY + countryCode;
        TopHeadlinesResponse response = ResponseCacheManager.getInstance().fetchResponse(cacheKey, TopHeadlinesResponse.class);
        if (response == null || CollectionUtils.isNullEmpty(response.getArticleList())) {
            return null;
        }
        return response.getArticleList();
    }

    public void setUpUI(String countryCode) {
        this.countryCode = countryCode;
        showProgressBar.set(true);
        apiManager.fetchTopHeadlines(countryCode)
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(response -> {
                    addDataToAdapterList(response.getArticleList());
                    showProgressBar.set(false);
                }, e -> {
                    List<NewsArticle> articleList = fetchNewsArticlesFromCache(countryCode);
                    if (CollectionUtils.isNullEmpty(articleList)) {
                        // show error view
                        showErrorView.set(true);
                    } else {
                        addDataToAdapterList(articleList);
                        Toast.makeText(GrabNewsApplication.mContext, "Could not load fresh headlines", Toast.LENGTH_LONG).show();
                    }
                    showProgressBar.set(false);
                });
    }

    public void onRefreshClicked() {
        setUpUI(countryCode);
    }

    private void addDataToAdapterList(List<NewsArticle> articleList) {
        if (CollectionUtils.isNullEmpty(articleList)) {
            return;
        }
        List<BaseRecyclerItemViewData> viewDataList = new ArrayList<>();
        for (NewsArticle article : articleList) {
            NewsItemViewModel itemViewModel = new NewsItemViewModel();
            itemViewModel.setTitle(article.getTitle());
            if(article.getSource() == null || StringUtils.isNullEmpty(article.getSource().getName())) {
                itemViewModel.setSourceText(StringUtils.isNotNullEmpty(article.getAuthor()) ? article.getAuthor() : "Anonymous source");
                itemViewModel.setActualSource("News");
            } else {
                itemViewModel.setSourceText(article.getSource().getName());
                itemViewModel.setActualSource(itemViewModel.getSourceText());
            }
            if (StringUtils.isNotNullEmpty(itemViewModel.getSourceText())) {
                itemViewModel.setSourceIcon(itemViewModel.getSourceText().charAt(0) + "");
            }
            itemViewModel.setImageUrl(article.getImageUrl());
            itemViewModel.setUrl(article.getUrl());
            itemViewModel.setListener(this);

            BaseRecyclerItemViewData viewData = new BaseRecyclerItemViewData(R.layout.news_item_layout);
            viewData.addBindingViewModel(BR.model, itemViewModel);

            viewDataList.add(viewData);
        }
        adapterList.addAll(viewDataList);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroyActivity() {
        compositeDisposable.clear();
    }

    public LiveData<BaseEvent> getEventLiveData() {
        return eventLiveData;
    }

    @Override
    public void onNewsItemClicked(@NonNull NewsItemViewModel model) {
        Bundle args = new Bundle();
        args.putString(AppConstants.BUNDLE_KEY_WEBVIEW_TITLE, model.getActualSource());
        args.putString(AppConstants.BUNDLE_KEY_WEBVIEW_URL, model.getUrl());
        LaunchActivityEvent launchActivityEvent = new LaunchActivityEvent(WebViewActivity.class, args);
        eventLiveData.setValue(launchActivityEvent);
    }
}

package com.bitfault.grabnews.common.core;

import android.os.AsyncTask;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ThreadPoolManager {

    private Executor executor;
    private Executor networkExecutor;

    @Inject
    public ThreadPoolManager() {
        executor = AsyncTask.THREAD_POOL_EXECUTOR;
        networkExecutor = Executors.newFixedThreadPool(8);
    }

    public Executor getExecutor() {
        return executor;
    }

    public Executor getNetworkExecutor() {
        return networkExecutor;
    }
}

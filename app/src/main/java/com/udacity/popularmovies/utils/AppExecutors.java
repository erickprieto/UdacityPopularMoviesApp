/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.udacity.popularmovies.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 *
 * @author Erick Prieto
 * @since 2018
 */
public class AppExecutors {

    private static final int NETWORK_THREAD_COUNT = 3;

    private static Executor diskIOExecutor;

    private static Executor networkIOExecutor;

    private static Executor mainThreadExecutor;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIOExecutor = diskIO;
        this.networkIOExecutor = networkIO;
        this.mainThreadExecutor = mainThread;
    }

    private AppExecutors() {
        this(Executors.newSingleThreadExecutor()
                , Executors.newFixedThreadPool(NETWORK_THREAD_COUNT)
                , new MainThreadExecutor());
    }

    public static Executor getDiskIO() {
        if (diskIOExecutor == null) { new AppExecutors(); }
        return diskIOExecutor;
    }

    public static Executor getNetworkIO() {
        if (networkIOExecutor == null) { new AppExecutors(); }
        return networkIOExecutor;
    }

    public static Executor getMainThread() {
        if(getMainThread() == null) { new AppExecutors(); }
        return mainThreadExecutor;
    }

    private static class MainThreadExecutor implements Executor {

        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
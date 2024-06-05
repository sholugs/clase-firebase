package com.example.firebase.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorManager {
    private ExecutorService executorService;

    public ExecutorManager() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}

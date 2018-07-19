package com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.boisneyphilippe.githubarchitecturecomponents.executors.AppExecutors;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkBoundResource(AppExecutors appExecutors, boolean isOnlyNetworkCall) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));

        LiveData<ResultType> dbSource = performDatabaseOperation();

        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> result.setValue(Resource.success(newData)));
            }
        });
    }

    private  void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appExecutors.diskIO().execute(() -> {
                    saveCallResult(processResponse(response));
                    appExecutors.mainThread().execute(() ->
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(performDatabaseOperation(),
                                    newData -> result.setValue(Resource.success(newData)))
                    );
                });
            } else {
                onFetchFailed();
                result.addSource(dbSource,
                        newData -> result.setValue(Resource.error(response.errorMessage, newData)));
            }
        });
    }

    protected  void onFetchFailed() {
    }

    public  LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected  RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract  void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract  boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract  LiveData<ResultType> performDatabaseOperation();

    @NonNull
    @MainThread
    protected abstract  LiveData<ApiResponse<RequestType>> createCall();
}

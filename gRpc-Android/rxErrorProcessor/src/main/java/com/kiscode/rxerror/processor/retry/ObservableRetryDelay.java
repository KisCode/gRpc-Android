package com.kiscode.rxerror.processor.retry;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Description:重试
 * Author:
 * Date : 2020/12/15 11:21
 **/
public class ObservableRetryDelay implements Function<Observable<Throwable>, ObservableSource<?>> {

    private static final String TAG = "ObservableRetryDelay";
    private RetryConfig retryConfig;
    private int retryCount = 0;

    public ObservableRetryDelay(RetryConfig retryConfig) {
        this.retryConfig = retryConfig;
    }

    @Override
    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
        final int maxRetryCount = retryConfig.getMaxRetryCount();
        final long delayTime = retryConfig.getDelayTime();

        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                if (++retryCount <= maxRetryCount) {
                    Log.i(TAG, "retryCount:" + retryCount);
                    return Observable.timer(delayTime,
                            TimeUnit.MILLISECONDS);
                }
                return Observable.error(throwable);
            }
        });
    }
}
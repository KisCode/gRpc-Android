package com.kiscode.rxerror.processor;

import android.util.Log;

import com.kiscode.rxerror.processor.retry.ObservableRetryDelay;
import com.kiscode.rxerror.processor.retry.RetryConfig;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Description: 全局异常处理
 * Author: keno
 * Date : 2020/12/14 13:32
 **/
public class GlobalErrorTransformer<T> implements ObservableTransformer<T, T> {
    private static final String TAG = "GlobalErrorTransformer";

    private Function<Throwable, Observable<T>> globalOnErrorResume;
    private Function<T, Observable<T>> globalOnNextRetryInterceptor;
    private RetryConfig globalRetryConfig;

    public GlobalErrorTransformer(Function<T, Observable<T>> globalOnNextRetryInterceptor,
                                  Function<Throwable, Observable<T>> globalOnErrorResume,
                                  RetryConfig retryConfig
    ) {
        this.globalOnErrorResume = globalOnErrorResume;
        this.globalOnNextRetryInterceptor = globalOnNextRetryInterceptor;
        this.globalRetryConfig = retryConfig;
    }

    @NonNull
    @Override
    public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
        return upstream
                .flatMap(new Function<T, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(final T t) throws Exception {
                        //转换
                        Log.i(TAG, "flatMap apply: " + t);
                        return globalOnNextRetryInterceptor.apply(t);
                    }
                })
                .retryWhen(new ObservableRetryDelay(globalRetryConfig))
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(final Throwable throwable) throws Exception {
                        //异常处理
                        Log.i(TAG, "onErrorResumeNext apply: " + throwable);
                        return globalOnErrorResume.apply(throwable);
                    }
                });
    }
}
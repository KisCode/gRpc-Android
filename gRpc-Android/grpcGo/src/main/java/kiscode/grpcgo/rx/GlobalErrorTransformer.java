package kiscode.grpcgo.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2020/12/14 13:32
 **/
public class GlobalErrorTransformer<T> implements ObservableTransformer<T, T> {

    private Function<Throwable, Observable<T>> globalOnErrorResume;
    private Function<T, Observable<T>> globalOnNextRetryInterceptor;

    public GlobalErrorTransformer(Function<Throwable, Observable<T>> globalOnErrorResume, Function<T, Observable<T>> globalOnNextRetryInterceptor) {
        this.globalOnErrorResume = globalOnErrorResume;
        this.globalOnNextRetryInterceptor = globalOnNextRetryInterceptor;
    }

    @NonNull
    @Override
    public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
        return upstream
                .flatMap(new Function<T, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(final T t) throws Exception {
                        //重试
                        return globalOnNextRetryInterceptor.apply(t);
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(final Throwable throwable) throws Exception {
                        //异常处理
                        return globalOnErrorResume.apply(throwable);
                    }
                });
    }
}
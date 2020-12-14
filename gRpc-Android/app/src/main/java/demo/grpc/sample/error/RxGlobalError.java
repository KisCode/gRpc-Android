package demo.grpc.sample.error;

import android.support.v4.app.FragmentActivity;

import io.grpc.StatusRuntimeException;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import kiscode.grpcgo.rx.GlobalErrorTransformer;

/**
 * Description:
 * Author: keno
 * Date : 2020/12/14 13:37
 **/
public class RxGlobalError {
    public static <T> GlobalErrorTransformer<T> handleGlobalError(final FragmentActivity context) {
        return new GlobalErrorTransformer<T>(new Function<Throwable, Observable<T>>() {
            @Override
            public Observable<T> apply(@NonNull Throwable throwable) throws Exception {
                if (throwable instanceof StatusRuntimeException) {
                    GrpcErroProcessor.handleError(context, (StatusRuntimeException) throwable);
                }
                return Observable.error(throwable);
            }
        }, new Function<T, Observable<T>>() {
            @Override
            public Observable<T> apply(@NonNull T t) throws Exception {
                //重试
                return Observable.just(t);
            }
        });
    }
}
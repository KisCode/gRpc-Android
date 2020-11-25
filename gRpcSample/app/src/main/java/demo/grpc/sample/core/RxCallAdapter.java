package demo.grpc.sample.core;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * Description:
 * Author: KENO
 * Date : 2020/11/19 13:24
 **/
public class RxCallAdapter implements CallAdapter<Observable<?>> {
    private final Type responseType;

    public RxCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    public Type getResponseType() {
        return responseType;
    }

    @Override
    public <R> Observable<R> adapt(final Call<R> call) throws Exception {
        Log.i("grpc", "RxCallAdapter adapt");
//            rawCall.getServiceMethod().rpcMananger.
        //1.执行
        //2.转换 convert
//        return (T) call.excute();

//        final Observable.OnSubscribe onSubscribe = new CallOnSubscribe(call);
//        Observable<R> observable = Observable.create(onSubscribe);
        ObservableOnSubscribe<R> source = new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) {
               /* try {
                    R response = call.execute();
                    emitter.onNext(response);
                } catch (Throwable e) {
                    e.printStackTrace();
                    Log.i("RxCallAdapter",e.getMessage());
                    emitter.onError(e);
                }*/

                try {
                    call.execute();
                } catch (Throwable e) {
                    if (e instanceof InvocationTargetException) {
                        Throwable exception = ((InvocationTargetException) e).getTargetException();
                        Log.i("RxCallAdapterETarget", "" + exception);
                    }
                    Log.i("RxCallAdapterE", "" + e);
                }
            }
        };
        return Observable.create(source);
    }
/*
    static final class CallOnSubscribe<T> implements Observable.OnSubscribe<T> {
        private final Call<T> originalCall;

        CallOnSubscribe(Call<T> originalCall) {
            this.originalCall = originalCall;
        }

        @Override
        public void call(Subscriber<? super T> subscriber) {
            // Since Call is a one-shot type, clone it for each new subscriber.
            Call<T> call = originalCall.clone();
            RequestArbiter<T> requestArbiter = new RequestArbiter(call, subscriber);
            subscriber.add(requestArbiter);
            subscriber.setProducer(requestArbiter);
        }
    }

    static final class RequestArbiter<T> extends AtomicBoolean implements Subscription, Producer {
        private final Call<T> call;
        private final Subscriber<? super T> subscriber;

        RequestArbiter(Call<T> call, Subscriber<? super T> subscriber) {
            this.call = call;
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            if (n < 0) throw new IllegalArgumentException("n < 0: " + n);
            if (n == 0) return; // Nothing to do when requesting 0.
            if (!compareAndSet(false, true)) return; // Request was already triggered.

            try {
                T response = call.execute();
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(response);
                }
            } catch (Throwable t) {
                Exceptions.throwIfFatal(t);
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(t);
                }
                return;
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        }

        @Override
        public void unsubscribe() {
//            call.cancel();
        }

        @Override
        public boolean isUnsubscribed() {
//            return call.isCanceled();
            return false;
        }
    }*/
}
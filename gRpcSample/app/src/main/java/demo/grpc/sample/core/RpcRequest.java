package demo.grpc.sample.core;

import java.lang.reflect.Proxy;

import grpc.sample.UserReq;
import grpc.sample.UserResp;
import grpc.sample.UserServiceGrpc;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2020/11/12 15:31
 **/
public class RpcRequest {

    public static void test() {
//        Observable<UserResp> observable = get(null);
    }

    public static <T> T get() {
        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(RPCMananger.getChannel());

        UserResp response = stub.getUser(null);
        return null;
    }

    public static <T> Observable<ReturnWrapper<T>> getObservable(final UserReq userReq) {
        return Observable.create(new ObservableOnSubscribe<ReturnWrapper<T>>() {
            @Override
            public void subscribe(ObservableEmitter<ReturnWrapper<T>> emitter) throws Exception {
                UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(RPCMananger.getChannel());

                try {
                    UserResp response = stub.getUser(userReq);

                    emitter.onNext(new ReturnWrapper<T>((T) response));
                } catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        });
    }

    public static Observable<UserResp> getUser(final UserReq userReq) {

        return Observable.create(new ObservableOnSubscribe<UserResp>() {
            @Override
            public void subscribe(ObservableEmitter<UserResp> emitter) throws Exception {
                UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(RPCMananger.getChannel());
                try {
                    UserResp response = stub.getUser(userReq);
                    emitter.onNext(response);
                } catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        });
    }

    public  Observable<UserResp> getUser2(final UserReq userReq) {

        return Observable.create(new ObservableOnSubscribe<UserResp>() {
            @Override
            public void subscribe(ObservableEmitter<UserResp> emitter) throws Exception {
                UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(RPCMananger.getChannel());
                try {
                    UserResp response = stub.getUser(userReq);
                    emitter.onNext(response);
                } catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        });
    }


} 
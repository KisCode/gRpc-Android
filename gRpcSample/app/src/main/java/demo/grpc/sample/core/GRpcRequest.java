package demo.grpc.sample.core;


import grpc.sample.UserReq;
import grpc.sample.UserResp;
import grpc.sample.UserServiceGrpc;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/11/12 23:22
 */

public interface GRpcRequest {

    Observable<UserResp> getUser2(final UserReq userReq);
}


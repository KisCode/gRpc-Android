package demo.grpc.sample.api;


import demo.grpc.sample.core.annotation.GrpcAnnotaion;
import grpc.sample.UserReq;
import grpc.sample.UserResp;
import grpc.sample.UserServiceGrpc;
import io.reactivex.Observable;

/****
 * Description: GRPC网络请求API
 * Author:  keno
 * CreateDate: 2020/11/12 23:22
 */

public interface GRpcApi {

    @GrpcAnnotaion(className = UserServiceGrpc.class, methodName = "getUser")
    UserResp getUser(final UserReq userReq);


    @GrpcAnnotaion(className = UserServiceGrpc.class, methodName = "getUser")
    Observable<UserResp> getUserObservable(final UserReq userReq);


    @GrpcAnnotaion(className = UserServiceGrpc.class, methodName = "getUser")
    String getUserString(final UserReq userReq);
}


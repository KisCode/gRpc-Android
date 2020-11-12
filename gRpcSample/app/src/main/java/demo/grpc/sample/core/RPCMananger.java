package demo.grpc.sample.core;

import demo.grpc.sample.interceptor.HeaderClientInterceptor;
import grpc.sample.UserReq;
import grpc.sample.UserResp;
import grpc.sample.UserServiceGrpc;
import io.grpc.Channel;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2020/11/12 15:06
 **/
public class RPCMananger {
    public static <T, R> T get(R r) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("grpctest.test.rlair.net")
                .useTransportSecurity()
                .build();
        HeaderClientInterceptor headerClientInterceptor = new HeaderClientInterceptor("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJFZmIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoibGlob25namlhbmciLCJqdGkiOiJjNGUxMmIwOS00MGU4LTQ0YmQtYWU2MC01MWNlYjBlNjM4ZDAiLCJpYXQiOiIxNjA1MDYzODM1IiwibmJmIjoxNjA1MDYzODM1LCJleHAiOjE2MDUxMDcwMzUsImlzcyI6ImVmYi5ybGFpci5uZXQiLCJhdWQiOiJFZmIifQ.09Iqs031U4XdkaXzsKXI41JrVmepCicfgKWX9H_Fy0f1PMnJKb8TkI1LS8Z0sDKqRRNE4uoYDk1dP1hHJpvFEEmTczsufc8b6dOXrAz5t1cJ5-SUJvaLf_6ZapDSkg7GHm0OcODYBu9jxKNVj0KjbUAQu_Q8Pbu_x-ETScFRtqiTwhTehwmQ_2oZe_426Q9tf79P4xBoFOlcT0pxXGJ8ViVX_7cIugaTxFpGuFiMp7rREOoj-qBkMxMdpHmxQnYeWN0tTvPSEBBXV10an7GwRULxMn7StLqX-diSJTQkivOJCB_G-7cRelQCjhp9zrUj2gcucHFeIlhtgEECHV00Lw");
        Channel channel = ClientInterceptors.intercept(managedChannel, headerClientInterceptor);
        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
        UserReq userReq = UserReq.newBuilder().setName("Android").build();
        try {
            UserResp response = stub.getUser(userReq);
        } catch (Exception ex) {
        }
        return null;
    }


    public static Channel getChannel() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("grpctest.test.rlair.net")
                .useTransportSecurity()
                .build();
        HeaderClientInterceptor headerClientInterceptor = new HeaderClientInterceptor("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJFZmIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoibGlob25namlhbmciLCJqdGkiOiJjNGUxMmIwOS00MGU4LTQ0YmQtYWU2MC01MWNlYjBlNjM4ZDAiLCJpYXQiOiIxNjA1MDYzODM1IiwibmJmIjoxNjA1MDYzODM1LCJleHAiOjE2MDUxMDcwMzUsImlzcyI6ImVmYi5ybGFpci5uZXQiLCJhdWQiOiJFZmIifQ.09Iqs031U4XdkaXzsKXI41JrVmepCicfgKWX9H_Fy0f1PMnJKb8TkI1LS8Z0sDKqRRNE4uoYDk1dP1hHJpvFEEmTczsufc8b6dOXrAz5t1cJ5-SUJvaLf_6ZapDSkg7GHm0OcODYBu9jxKNVj0KjbUAQu_Q8Pbu_x-ETScFRtqiTwhTehwmQ_2oZe_426Q9tf79P4xBoFOlcT0pxXGJ8ViVX_7cIugaTxFpGuFiMp7rREOoj-qBkMxMdpHmxQnYeWN0tTvPSEBBXV10an7GwRULxMn7StLqX-diSJTQkivOJCB_G-7cRelQCjhp9zrUj2gcucHFeIlhtgEECHV00Lw");
        Channel channel = ClientInterceptors.intercept(managedChannel, headerClientInterceptor);
        return channel;
    }
} 
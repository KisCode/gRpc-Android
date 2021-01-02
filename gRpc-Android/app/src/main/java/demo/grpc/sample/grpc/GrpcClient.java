package demo.grpc.sample.grpc;

import com.kiscode.grpcgo.adapter.rxjava.RxCallAdapterFactory;

import java.util.HashMap;

import kiscode.grpcgo.GrpcGo;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2020/12/9 16:54
 **/
public class GrpcClient {
    private static HashMap<String, GrpcGo> pools = new HashMap<>();

    public static GrpcGo getInstance(String host) {
        GrpcGo instance = pools.get(host);
        if (instance == null) {
            instance = new GrpcGo.Builder()
                    .baseUrl(host)
                    .useTransportSecurity(true)
                    .addCallAdapterFactory(RxCallAdapterFactory.create())
//                    .setHeaderFactory(OAHeaderFactory.create())
                    .build();
            pools.put(host, instance);
        }
        return instance;
    }
}
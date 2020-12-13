package demo.grpc.sample.grpc;

import java.util.HashMap;

import demo.grpc.sample.header.OAHeaderFactory;
import kiscode.grpcgo.GrpcGo;
import kiscode.grpcgo.RxCallAdapterFactory;

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
                    .addCallAdapterFactory(RxCallAdapterFactory.create())
//                    .setHeaderFactory(OAHeaderFactory.create())
                    .build();
            pools.put(host, instance);
        }
        return instance;
    }
}
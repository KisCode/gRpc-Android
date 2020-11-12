package demo.grpc.sample.interceptor;

import java.util.HashMap;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2020/11/11 14:52
 **/
public class HeaderClientInterceptor implements ClientInterceptor {

    private HashMap<String, String> mHeaderMap;

    public HeaderClientInterceptor(HashMap<String, String> header) {
        this.mHeaderMap = header;
    }

    public HeaderClientInterceptor(String key, String value) {
        this.mHeaderMap = new HashMap<>();
        mHeaderMap.put(key, value);
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                //将请求头拼接至header中
                if (mHeaderMap != null && !mHeaderMap.isEmpty()) {
                    for (String key : mHeaderMap.keySet()) {
                        Metadata.Key<String> tokenKey = Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER);
                        headers.put(tokenKey, mHeaderMap.get(key));
                    }
                }

                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        /**
                         * if you don't need receive header from server,
                         * you can use {@link io.grpc.stub.MetadataUtils attachHeaders}
                         * directly to send header
                         */
//                        Logger.i(TAG, "header received from server:" + headers);
                        super.onHeaders(headers);
                    }
                }, headers);
            }
        };
    }
} 
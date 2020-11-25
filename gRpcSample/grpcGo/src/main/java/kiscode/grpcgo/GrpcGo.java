package kiscode.grpcgo;

import android.text.TextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Author: KENO
 * Date : 2020/11/12 15:06
 **/
public class GrpcGo {
    private static final String TAG = "GrpcGo";
    private final Map<Method, ServiceMethod> serviceMethodCache = new LinkedHashMap<>();

    private String baseUrl;
    private HeaderFactory headerFactory;
    private List<CallAdapter.Factory> adapterFactories;

    public GrpcGo(String baseUrl, HeaderFactory headerFactory, List<CallAdapter.Factory> adapterFactories) {
        this.baseUrl = baseUrl;
        this.headerFactory = headerFactory;
        this.adapterFactories = adapterFactories;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public HeaderFactory getHeaderFactory() {
        return headerFactory;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }

        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }

        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, final Object[] args) throws Throwable {
                // 1. invoke执行方法
                //2. 根据返回值类型进行转换
                ServiceMethod serviceMethod = loadServiceMethod(method);
                GrpcCall grpcCall = new GrpcCall(serviceMethod, args);
                return serviceMethod.callAdapter.adapt(grpcCall);
            }
        });
    }


    ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod result;
        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(this, method).build();
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }

    public CallAdapter<?> callAdapter(Type returnType, Annotation[] annotations) {
        return nextCallAdapter(returnType, annotations);
    }

    private CallAdapter<?> nextCallAdapter(Type returnType, Annotation[] annotations) {
        for (CallAdapter.Factory adapterFactory : adapterFactories) {
            CallAdapter<?> callAdapter = adapterFactory.get(returnType);
            if (callAdapter != null) {
                return callAdapter;
            }
        }
        throw new IllegalArgumentException("Not Found CallAdapter");
    }


    public static final class Builder {
        private String baseUrl;
        private HeaderFactory headerFactory;
        private List<CallAdapter.Factory> adapterFactories = new ArrayList<>();

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setHeaderFactory(HeaderFactory headerFactory) {
            this.headerFactory = headerFactory;
            return this;
        }

        public Builder addCallAdapterFactory(CallAdapter.Factory callAdapterFactory) {
            adapterFactories.add(callAdapterFactory);
            return this;
        }

        public GrpcGo build() {
            if (TextUtils.isEmpty(this.baseUrl)) {
                throw new IllegalArgumentException("Not set baseUrl");
            }

            if (headerFactory == null) {
                headerFactory = new DefaultHeaderClientInterceptor();
            }

            if (adapterFactories.isEmpty()) {
                adapterFactories.add(DefaultCallAdapterFactory.create());
            }

            return new GrpcGo(baseUrl, headerFactory, adapterFactories);
        }
    }
} 
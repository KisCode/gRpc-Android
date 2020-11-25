package kiscode.grpcgo;

import java.lang.reflect.Type;

/**
 * Description:
 * Author: keno
 * Date : 2020/11/25 16:38
 **/
public class DefaultCallAdapterFactory extends CallAdapter.Factory {
    public static CallAdapter.Factory create() {
        return new DefaultCallAdapterFactory();
    }

    @Override
    CallAdapter<?> get(Type returnType) {
        return new DefaultCallAdapter();
    }
}
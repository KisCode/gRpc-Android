package demo.grpc.sample.core;


import java.lang.reflect.Type;

/**
 * Description:
 * Author: KENO
 * Date : 2020/11/19 13:18
 **/
public interface CallAdapter<T> {
    <R> T adapt(Call<R> call) throws Exception;
}

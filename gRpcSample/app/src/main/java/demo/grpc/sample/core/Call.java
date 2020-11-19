package demo.grpc.sample.core;

/**
 * Description:
 * Author: KENO
 * Date : 2020/11/19 13:20
 **/
public interface Call<T> {
    T excute() throws Exception;
}

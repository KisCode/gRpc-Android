package demo.grpc.sample.core;

/**
 * Description:
 * Author: KENO
 * Date : 2020/11/19 13:20
 **/
public interface Call<T> {
    T execute() throws Exception;

    /**
     * Create a new, identical call to this one which can be enqueued or executed even if this call
     * has already been.
     */
    Call<T> clone();
}

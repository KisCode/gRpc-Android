package demo.grpc.sample.core;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2020/11/12 16:37
 **/
public class ReturnWrapper<T> {
    private T data;

    public ReturnWrapper(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
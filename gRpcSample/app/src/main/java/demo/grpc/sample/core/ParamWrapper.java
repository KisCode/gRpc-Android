package demo.grpc.sample.core;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2020/11/12 16:37
 **/
public class ParamWrapper<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
} 
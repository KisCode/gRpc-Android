package kiscode.grpcgo.throwable;

import android.util.Log;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2020/12/10 14:56
 **/
public class GrpcErrorHandler {
    public static String handle(Throwable throwable) {
        if (throwable instanceof StatusRuntimeException) {
            Log.e("GrpcErrorHandler", throwable.toString());
            StatusRuntimeException ex = (StatusRuntimeException) throwable;
            Status status = ex.getStatus();
            if (status.getCode() == Status.Code.UNAUTHENTICATED) {
//                Log.i(TAG, "请求未授权：UNAUTHENTICATED");
            } else {
//                Log.e(TAG, ex.toString());
            }
        }
        return "";
    }
} 
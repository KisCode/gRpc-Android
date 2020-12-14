package demo.grpc.sample.error;

import android.content.Context;
import android.widget.Toast;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;

/**
 * Description:
 * Date : 2020/12/14 15:00
 **/
public class GrpcErroProcessor {
    public static void handleError(Context context, StatusRuntimeException statusRuntimeException) {
        Status status = statusRuntimeException.getStatus();
        switch (status.getCode()) {
            case UNAUTHENTICATED:
                Toast.makeText(context, "401未授权:" + status.getDescription(), Toast.LENGTH_SHORT).show();
                break;
            case ABORTED:
            case UNKNOWN:
            case INTERNAL:
            case CANCELLED:
            case DATA_LOSS:
            case NOT_FOUND:
            case UNAVAILABLE:
            case OUT_OF_RANGE:
            case UNIMPLEMENTED:
            case ALREADY_EXISTS:
            case INVALID_ARGUMENT:
            case DEADLINE_EXCEEDED:
            case PERMISSION_DENIED:
            case RESOURCE_EXHAUSTED:
            case FAILED_PRECONDITION:
                Toast.makeText(context, "Exception:" + status.getDescription(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
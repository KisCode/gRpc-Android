package kiscode.grpcgo.throwable;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2020/12/10 14:53
 **/
public class GrpcGoException extends Exception {

    public GrpcGoException(String message, Throwable cause) {
        super(message, cause);
    }
}
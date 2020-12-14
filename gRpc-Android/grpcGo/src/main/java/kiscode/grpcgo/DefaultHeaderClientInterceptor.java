package kiscode.grpcgo;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Date : 2020/11/25 17:03
 **/
public class DefaultHeaderClientInterceptor extends HeaderFactory {
    @Override
    protected Map<String, String> createHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("DefaultHeader", "GrpcGo-Android");
        return headers;
    }
}
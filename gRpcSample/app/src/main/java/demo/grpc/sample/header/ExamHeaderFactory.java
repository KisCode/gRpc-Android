package demo.grpc.sample.header;


import java.util.LinkedHashMap;
import java.util.Map;

import kiscode.grpcgo.HeaderFactory;


/****
 * Description: 考试模块请求头工厂
 * Author:  keno
 * CreateDate: 2020/11/15 10:50
 */

public class ExamHeaderFactory extends HeaderFactory {
    @Override
    protected Map<String, String> createHeaders() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        headers.put("token", getOAToken());
        return headers;
    }

    private String getOAToken() {
        //具体业务逻辑代码
        return "Bearer exam-xxxxyyyyyzzzzz";
    }
}

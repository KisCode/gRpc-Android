package demo.grpc.sample.core.header;


import java.util.HashMap;

import demo.grpc.sample.core.HeaderFactory;

/****
 * Description: 考试模块请求头工厂
 * Author:  keno
 * CreateDate: 2020/11/15 10:50
 */

public class ExamHeaderFactory extends HeaderFactory {
    @Override
    protected HashMap<String, String> createHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", getOAToken());
        return headers;
    }

    private String getOAToken() {
        //具体业务逻辑代码
        return "Bearer exam-xxxxyyyyyzzzzz";
    }
}

package demo.grpc.sample.core.header;


import java.util.HashMap;
import java.util.Map;

import demo.grpc.sample.core.HeaderFactory;

/****
 * Description: OA模块请求头工厂
 * Author:  keno
 * CreateDate: 2020/11/15 10:50
 */

public class OAHeaderFactory extends HeaderFactory {

    private OAHeaderFactory() {
    }

    public static OAHeaderFactory create() {
        return new OAHeaderFactory();
    }

    @Override
    protected Map<String, String> createHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", getOAToken());
//        Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJFZmIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoibGlob25namlhbmciLCJqdGkiOiIyZTI4YWRiMi04ZTcwLTRjMjYtYjJhNS1hMjc0MTA2NDNmMTgiLCJpYXQiOiIxNjA1MjMyOTA4IiwibmJmIjoxNjA1MjMyOTA4LCJleHAiOjE2MDUyNzYxMDgsImlzcyI6ImVmYi5ybGFpci5uZXQiLCJhdWQiOiJFZmIifQ.FS9-S8stYBHHHPUV9mgzFUbsXjhsJMrfbgzCFrd_QFgeM6AO5EANCCsBMu8JbdaIxNV1FLufpdvZdyFpgqpMV9UFlY_0pdGyPVsByiWqgdZ2VkGeOy5YfL7wIG4KORlhZTLOqoA2-MNTjt0DkrY1Ex0AZPbhpe-VNdWXENqOJ0M2bgV9msd9llyozR_gKYjnSMnpwQDup4RRHRbZrEFYA5_rny_MKKEsKN2xe2IhEicXm1W3Q-g68vjfwCYphsUIwmCsiSVnY4sIEadIaDfI6lsWRUmNB518sqjAlpEcLJHQJ1yFtol-P5tDmlshYqB_q2-Q7pnjqqqypALac6PEYw
        return headers;
    }


    private String getOAToken() {
        //具体业务逻辑代码
        return "Bearer xxxxyyyyyzzzzz";
    }
}

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
        return "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJFZmIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiYmFpeHVlIiwianRpIjoiYjY0YWYxNzMtNGI1NS00YzljLWJjZDQtNThkYzY5ZGVjZTI3IiwiaWF0IjoiMTYwNjI3Mzg2OSIsIm5iZiI6MTYwNjI3Mzg2OSwiZXhwIjoxNjA2MzE3MDY5LCJpc3MiOiJlZmIucmxhaXIubmV0IiwiYXVkIjoiRWZiIn0.UsXz-I4uZlA5VeQU9bYknJLHhXjgRUmvjDh6FZqRy5n7eB3_STQ-GV7OOLk2z1MFUprFs2lsAVFyqztkNKWPAMBDpx53Z6HUXlWGarvTia0TseSyuDaL3YQ0jA3Jh6mDcw7yHGDxMKqiPNll-kgPQwXfC-7Yyk1UqXtfgWDPt2b7lK4oIwtZg4glCj3fC7i0JvQTzYNX9AGYoraktxpbV4ta_P3g44uyHkgf99A3Ewc7lDbVS8sZ_PM2L4Mogpn-FYboEUjWyjAYzrgQIvRcBaGErbtU6WGSpkZUe9P1i8jDkGQNHxTQp-PD1pQedOX6HcR24w6QnRxccMughvvIn";
    }
}

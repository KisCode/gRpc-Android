package demo.grpc.sample.core;


import java.util.Map;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/11/15 10:37
 */

public abstract class HeaderFactory {
    protected abstract Map<String, String> createHeaders();
}

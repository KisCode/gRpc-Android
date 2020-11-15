package demo.grpc.sample.core;


import java.util.HashMap;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/11/15 10:37
 */

public abstract class HeaderFactory {
    protected abstract HashMap<String, String> createHeaders();
}

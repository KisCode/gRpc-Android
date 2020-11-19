package demo.grpc.sample.annotation;

/**
 * Description:
 * Author: KENO
 * Date : 2020/11/13 10:12
 **/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 声明网络订阅事件
 * Author: keno
 * Date : 2020/9/3 21:25
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GrpcAnnotaion {
    Class<?> className();

    String methodName();
} 
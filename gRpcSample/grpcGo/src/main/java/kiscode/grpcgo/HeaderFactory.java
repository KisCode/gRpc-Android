package kiscode.grpcgo;


import java.util.Map;

/****
 * Description:  统一请求头创建接口
 * Author:  keno
 * CreateDate: 2020/11/15 10:37
 */
public abstract class HeaderFactory {
    protected abstract Map<String, String> createHeaders();
}

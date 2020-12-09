package kiscode.grpcgo;

/**
 * Description:
 * Author: keno
 * Date : 2020/11/25 15:53
 **/
public class DefaultCallAdapter implements CallAdapter<Object> {

    @Override
    public <R> Object adapt(Call<R> call) throws Exception {
        return call.execute();
    }
}
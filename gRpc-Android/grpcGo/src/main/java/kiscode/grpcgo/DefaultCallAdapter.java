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

   /* @Override
    public <R> ReturnModel<R> adapt(Call<R> call) throws Exception {
        ReturnModel<R> returnModel = new ReturnModel<>();
        R response = call.execute();
        returnModel.setData(response);
        return returnModel;
    }*/
}
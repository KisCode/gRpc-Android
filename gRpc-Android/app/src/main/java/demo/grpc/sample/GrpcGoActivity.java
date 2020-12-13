package demo.grpc.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import demo.grpc.sample.api.GRpcApi;
import demo.grpc.sample.grpc.GrpcClient;
import grpc.sample.UserReq;
import grpc.sample.UserResp;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class GrpcGoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GrpcGoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grpc_go);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_request_grpcgo).setOnClickListener(this);
        findViewById(R.id.btn_request_grpcgo_observable).setOnClickListener(this);
    }

    private void grpcRequest() {

        UserReq userReq = UserReq.newBuilder().setName("Android").build();
        GrpcClient.getInstance(getString(R.string.host))
                .create(GRpcApi.class)
                .getUserObservable(userReq)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserResp>() {
                    @Override
                    public void accept(UserResp userResp) throws Exception {
                        Log.i(TAG, "getUserObservable ：" + userResp.getName());
                        Toast.makeText(GrpcGoActivity.this, "请求成功：" + userResp.getName(), Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "StatusRuntimeException ：" + throwable);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_grpcgo:
                grpcRequest();
                break;
            case R.id.btn_request_grpcgo_observable:
                grpcRequest();
                break;
        }
    }
}
package demo.grpc.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import net.rlair.appmarket.grpc.AppVersionInfoQueryDto;
import net.rlair.appmarket.grpc.EOsType;
import net.rlair.appmarket.grpc.ExternalServiceGrpc;
import net.rlair.appmarket.grpc.GrpcResult_List_AppVersionInfoResultDto;

import demo.grpc.sample.api.AppmarkApi;
import demo.grpc.sample.grpc.GrpcClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.single.SingleDoOnError;
import kiscode.grpcgo.throwable.GrpcErrorHandler;

public class VersionListActivity extends AppCompatActivity implements View.OnClickListener {
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_list);

        findViewById(R.id.btn_request_version).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_version:
                requestVersionList();
//                requestVersionGRPC();
                break;
        }
    }

    private void requestVersionGRPC() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                ManagedChannel channel = ManagedChannelBuilder.forAddress("10.1.45.193", 5001)
                ManagedChannel channel = ManagedChannelBuilder.forTarget("10.1.45.193:5001")
//                        .useTransportSecurity() //传输安全的
                        .usePlaintext()
                        .build();

                AppVersionInfoQueryDto queryDto = AppVersionInfoQueryDto.newBuilder()
                        .setAppCode("net.rlair.aoc")
                        .setOsType(EOsType.Android)
                        .build();
                ExternalServiceGrpc.ExternalServiceBlockingStub stub = ExternalServiceGrpc.newBlockingStub(channel);

                try {
                    GrpcResult_List_AppVersionInfoResultDto appVersionInfoResult = stub.getAppVersionInfo(queryDto);
                    Log.i("onNext", "Code:" + appVersionInfoResult.getCode()
                            + "\n" + "Msg:" + appVersionInfoResult.getMsg()
                            + "\n" + "DataCount:" + appVersionInfoResult.getDataCount()
                    );
                } catch (Exception exception) {
                    GrpcErrorHandler.handle(exception);
                }
            }
        }).start();
    }

    private void requestVersionList() {
        AppVersionInfoQueryDto queryDto = AppVersionInfoQueryDto.newBuilder()
                .setAppCode("net.rlair.aoc")
                .setOsType(EOsType.Android)
                .build();
        String host = getString(R.string.AppMarketHost);
        Disposable disposable = GrpcClient.getInstance(host)
                .create(AppmarkApi.class)
                .getAppVersionList(queryDto)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GrpcResult_List_AppVersionInfoResultDto>() {
                    @Override
                    public void accept(GrpcResult_List_AppVersionInfoResultDto appVersionInfoResult) throws Exception {
                        Log.i("Throwable", appVersionInfoResult.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("Throwable", throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }


}
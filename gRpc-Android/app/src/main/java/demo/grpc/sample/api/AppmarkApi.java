package demo.grpc.sample.api;

import net.rlair.appmarket.grpc.AppDeviceInfoSaveDto;
import net.rlair.appmarket.grpc.AppLogSendDto;
import net.rlair.appmarket.grpc.AppUpdateInfoQueryDto;
import net.rlair.appmarket.grpc.AppVersionInfoQueryDto;
import net.rlair.appmarket.grpc.EventLogSendDto;
import net.rlair.appmarket.grpc.ExternalServiceGrpc;
import net.rlair.appmarket.grpc.GrpcResult_AppDeviceInfoSaveDto;
import net.rlair.appmarket.grpc.GrpcResult_AppUpdateInfoResultDto;
import net.rlair.appmarket.grpc.GrpcResult_List_AppVersionInfoResultDto;
import net.rlair.appmarket.grpc.grpcResult;

import io.reactivex.Observable;
import kiscode.grpcgo.annotation.GrpcAnnotaion;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2020/12/10 10:40
 **/
public interface AppmarkApi {

    /***
     * 获取版本信息
     * @param queryDto
     * @return
     */
    @GrpcAnnotaion(className = ExternalServiceGrpc.class, methodName = "getAppVersionInfo")
    Observable<GrpcResult_List_AppVersionInfoResultDto> getAppVersionList(final AppVersionInfoQueryDto queryDto);

    /***
     * 上传设备信息
     * @param deviceInfo
     * @return
     */
    @GrpcAnnotaion(className = ExternalServiceGrpc.class, methodName = "appDeviceInfoSave")
    Observable<GrpcResult_AppDeviceInfoSaveDto> saveAppDevice(final AppDeviceInfoSaveDto deviceInfo);

    /***
     * 获取更新信息
     * @param queryDto
     * @return
     */
    @GrpcAnnotaion(className = ExternalServiceGrpc.class, methodName = "getAppUpdateInfo")
    Observable<GrpcResult_AppUpdateInfoResultDto> getAppUpdateInfo(final AppUpdateInfoQueryDto queryDto);

    /***
     * 上传App用户行为日志信息
     * @param eventLog
     * @return
     */
    @GrpcAnnotaion(className = ExternalServiceGrpc.class, methodName = "sendAppEventLog")
    Observable<grpcResult> sendAppEventLog(final EventLogSendDto eventLog);

    /***
     * 上传异常日志信息
     * @param eventLog
     * @return
     */
    @GrpcAnnotaion(className = ExternalServiceGrpc.class, methodName = "sendAppLog")
    Observable<grpcResult> sendAppLog(final AppLogSendDto eventLog);

//    rpc AppDeviceInfoSave (AppDeviceInfoSaveDto) returns (GrpcResult_AppDeviceInfoSaveDto);
//    rpc CreateOrUpdate (DeviceSaveDto) returns (GrpcResult_DeviceSaveDto);
//    rpc GetAppUpdateInfo (AppUpdateInfoQueryDto) returns (GrpcResult_AppUpdateInfoResultDto);
//    rpc GetAppVersionInfo (AppVersionInfoQueryDto) returns (GrpcResult_List_AppVersionInfoResultDto);
//    rpc SendAppEventLog (EventLogSendDto) returns (grpcResult);
//    rpc SendAppLog (AppLogSendDto) returns (grpcResult);
} 
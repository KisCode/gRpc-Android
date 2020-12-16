package com.kiscode.rxerror.processor.retry;

/**
 * Description:重试配置条件
 * Author:
 * Date : 2020/12/15 11:23
 **/
public class RetryConfig {
    //默认重试一次
    private final int DEFAULT_MAX_RETRY_COUNT = 1;

    //默认300ms后重试
    private final int DEFAULT_DELAY_TIME = 300;

    private int maxRetryCount;
    private long delayTime;

    public RetryConfig() {
        this.maxRetryCount = DEFAULT_MAX_RETRY_COUNT;
        this.delayTime = DEFAULT_DELAY_TIME;
    }

    public RetryConfig(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
        this.delayTime = DEFAULT_DELAY_TIME;
    }

    public RetryConfig(long deleyTime) {
        this.delayTime = deleyTime;
        this.maxRetryCount = DEFAULT_MAX_RETRY_COUNT;
    }

    public RetryConfig(int maxRetryCount, long deleyTime) {
        this.maxRetryCount = maxRetryCount;
        this.delayTime = deleyTime;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public long getDelayTime() {
        return delayTime;
    }
}
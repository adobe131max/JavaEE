package edu.whu.springbootdemo.common;

import lombok.Data;

@Data
public class ApiInfo {

    private String apiName;

    private long callCount;

    private long totalResponseTime;

    private long maxResponseTime;

    private long minResponseTime;

    private long errorCount;

    public void incrementCallCount() {
        callCount++;
    }

    public void incrementErrorCount() {
        errorCount++;
    }

    public void updateResponseTime(long responseTime) {
        if (responseTime > maxResponseTime) {
            maxResponseTime = responseTime;
        }
        if (responseTime < minResponseTime) {
            minResponseTime = responseTime;
        }
        totalResponseTime += responseTime;
    }
}

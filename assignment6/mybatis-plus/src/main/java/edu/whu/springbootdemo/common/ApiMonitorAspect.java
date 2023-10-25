package edu.whu.springbootdemo.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aspect
@Component
public class ApiMonitorAspect {

    private final Map<String, ApiInfo> apiInfoMap = new ConcurrentHashMap<>();

    public Map<String, ApiInfo> getApiInfoMap() {
        return apiInfoMap;
    }

    @Pointcut("execution(* edu.whu.springbootdemo.controller.*.*(..))")
    public void apiMethods() {
    }

    @Around("apiMethods()")
    public Object monitorApi(ProceedingJoinPoint joinPoint) throws Throwable {
        String apiName = joinPoint.getSignature().toShortString();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            // 执行原始方法
            return joinPoint.proceed();
        } catch (Exception e) {
            // 在这里可以记录异常次数
            recordApiError(apiName);
            throw e;
        } finally {
            stopWatch.stop();
            recordApiInfo(apiName, stopWatch.getTotalTimeMillis());
            log.info(apiName + " cost time: " + stopWatch.getTotalTimeMillis());
        }
    }

    private void recordApiInfo(String apiName, long responseTime) {
        ApiInfo apiInfo = apiInfoMap.get(apiName);
        if (apiInfo == null) {
            apiInfo = new ApiInfo();
            apiInfo.setApiName(apiName);
        }
        apiInfo.incrementCallCount();
        apiInfo.updateResponseTime(responseTime);
        apiInfoMap.put(apiName, apiInfo);
    }

    private void recordApiError(String apiName) {
        ApiInfo apiInfo = apiInfoMap.get(apiName);
        if (apiInfo == null) {
            apiInfo = new ApiInfo();
            apiInfo.setApiName(apiName);
        }
        apiInfo.incrementErrorCount();
        apiInfoMap.put(apiName, apiInfo);
    }
}

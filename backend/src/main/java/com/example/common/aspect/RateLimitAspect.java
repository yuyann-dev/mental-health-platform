package com.example.common.aspect;

import com.example.common.enums.ResultCodeEnum;
import com.example.common.zj.RateLimit;
import com.example.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
@Slf4j
public class RateLimitAspect {
    //限制频率
    private ConcurrentHashMap<String, AtomicInteger> requestCountMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Long> lastAccessTimeMap = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();

        HttpServletRequest request = null;
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
                break;
            }
        }

        if (request != null) {
            String ipAddress = getClientIpAddress(request);
            String key = ipAddress + ":" + method.getDeclaringClass().getName() + "." + method.getName();

            long currentAccessTime = System.currentTimeMillis();
            Long lastAccessTime = lastAccessTimeMap.getOrDefault(key, 0L);
            long timeInterval = TimeUnit.MILLISECONDS.toMillis(currentAccessTime - lastAccessTime);

            synchronized (this) {
                if (timeInterval < rateLimit.duration() * 1000) {
                    AtomicInteger requestCount = requestCountMap.computeIfAbsent(key, k -> new AtomicInteger(0));
                    if (requestCount.incrementAndGet() > rateLimit.value()) {
                        log.warn("IP {} exceeded rate limit for method {}", ipAddress, method.getName());
                        throw new CustomException(ResultCodeEnum.TooManyrequestsFromThisIPPleaseTryAgainLater);                    }
                } else {
                    requestCountMap.put(key, new AtomicInteger(1));
                    lastAccessTimeMap.put(key, currentAccessTime);
                }
            }
        }

        // 继续执行原方法
        return joinPoint.proceed();
    }

    private String getClientIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
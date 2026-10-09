package com.example.common.zj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    // 默认最大访问次数
    int value() default 3;
    // 默认时间窗口（秒）
    long duration() default 60;
}
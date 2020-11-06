package com.ls.graver.Interceptor.impl;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class TimeInterceptor {
    @RuntimeType
    public static Object intercept(
            @This Object obj,
            @AllArguments Object[] allArguments,
            @Origin Method method,
            @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            // 原有函数执行
            return callable.call();
        } finally {
            System.out.println(allArguments.toString() + method + ": took " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}

package com.ls.graver.Interceptor.impl;

import net.bytebuddy.implementation.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 链路总委托类
 */
public class TraceInterceptor {

    @RuntimeType
    public static Object intercept(
            @This Object obj,
            @AllArguments Object[] allArguments,
            @Origin Method method,
            @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        RequestInterceptor requestInterceptor = new RequestInterceptor();
        InvokeHandlerMethodInterceptor invokeHandlerMethodInterceptor = new InvokeHandlerMethodInterceptor();
        RequestMapping basePath = obj.getClass().getAnnotation(RequestMapping.class);
        String path = "";
        if (basePath != null) {
            if (basePath.value().length > 0) {
                path = basePath.value()[0];
            } else if (basePath.path().length > 0) {
                path = basePath.path()[0];
            }
        }
        RequestMapping methodPath = method.getAnnotation(RequestMapping.class);
        if (methodPath != null) {
            if (methodPath.value().length > 0) {
                path += methodPath.value()[0];
            } else if (methodPath.path().length > 0) {
                path += methodPath.path()[0];
            }
        }
        System.out.println(path);
        invokeHandlerMethodInterceptor.beforeMethod(null, allArguments, method);
        requestInterceptor.beforeMethod(null, allArguments, method);
        try {
            // 原有函数执行
            return callable.call();
        } finally {
            System.out.println(method + ": took " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}

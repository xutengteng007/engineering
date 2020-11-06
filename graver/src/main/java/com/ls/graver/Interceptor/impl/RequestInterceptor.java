package com.ls.graver.Interceptor.impl;

import com.ls.graver.Interceptor.InstanceAroundInterceptor;
import com.ls.graver.Interceptor.Tool;
import com.ls.graver.context.ContextManager;
import com.ls.graver.context.RequestHolder;

import java.lang.reflect.Method;

/**
 * 请求委托类
 */
public class RequestInterceptor implements InstanceAroundInterceptor {

    public void beforeMethod(Tool inobj, Object[] allArguments, Method method) {
        // 获取当前请求的url
        RequestHolder request = (RequestHolder) ContextManager.getRuntimeContext()
                .get("LS_REQUEST");
        System.out.println(request.requestURL());
    }
}

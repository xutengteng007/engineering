package com.ls.graver.Interceptor.impl;

import com.ls.graver.Interceptor.InstanceAroundInterceptor;
import com.ls.graver.Interceptor.Tool;
import com.ls.graver.context.ContextManager;
import com.ls.graver.context.JavaxServletRequestHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 添加request上下文
 */
public class InvokeHandlerMethodInterceptor implements InstanceAroundInterceptor {

    @Override
    public void beforeMethod(Tool inobj, Object[] allArguments, Method method) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        ContextManager.getRuntimeContext().put("LS_REQUEST", new JavaxServletRequestHolder(request));
    }
}

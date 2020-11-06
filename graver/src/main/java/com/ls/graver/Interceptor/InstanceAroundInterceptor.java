package com.ls.graver.Interceptor;

import java.lang.reflect.Method;

public interface InstanceAroundInterceptor {

    public void beforeMethod(Tool inobj, Object[] allArguments, Method method);
}

package com.ls.graver.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 当前运行上下文
 */
public class RuntimeContext {

    private final ThreadLocal<RuntimeContext> contextThreadLocal;

    private Map<Object, Object> context = new ConcurrentHashMap<>(0);

    public RuntimeContext(ThreadLocal<RuntimeContext> contextThreadLocal) {
        this.contextThreadLocal = contextThreadLocal;
    }

    public void put(Object key, Object value) {
        context.put(key, value);
    }

    public Object get(Object key) {
        return context.get(key);
    }


}

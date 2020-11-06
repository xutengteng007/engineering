package com.ls.graver.context;

import com.ls.graver.context.RequestHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * 当前请求持有者
 */
public class JavaxServletRequestHolder implements RequestHolder {

    private final HttpServletRequest request;

    public JavaxServletRequestHolder(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getHeader(final String headerName) {
        return request.getHeader(headerName);
    }

    @Override
    public Enumeration<String> getHeaders(final String headerName) {
        return request.getHeaders(headerName);
    }

    @Override
    public String requestURL() {
        return request.getRequestURL().toString();
    }

    @Override
    public String requestMethod() {
        return request.getMethod();
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return request.getParameterMap();
    }
}

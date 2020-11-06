package com.ls.graver.context;

import java.util.Enumeration;
import java.util.Map;

public interface RequestHolder {
    String getHeader(String headerName);

    Enumeration<String> getHeaders(String headerName);

    String requestURL();

    String requestMethod();

    Map<String, String[]> getParameterMap();
}

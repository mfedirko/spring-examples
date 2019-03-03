package com.example.core.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.Assert;

public class RequestContextWrapper {
    private static final ThreadLocal<RequestContextWrapper> context = new ThreadLocal<>();

    public static RequestContextWrapper getCurrentContext(){
        RequestContextWrapper requestContextWrapper = context.get();
        if (requestContextWrapper == null){
            setContext(createEmptyContext());
            requestContextWrapper = context.get();
        }
        return requestContextWrapper;
    }

    public static void setContext(RequestContextWrapper context){
        Assert.notNull(context,"Request context cannot be null");
        RequestContextWrapper.context.set(context);
    }

    private static RequestContextWrapper createEmptyContext(){
        return new RequestContextWrapper();
    }

    private HttpServletRequest request;
    private HttpServletResponse response;
    private String currentModule;

    public String getCurrentModule() {
        return currentModule;
    }

    public void setCurrentModule(String currentModule) {
        this.currentModule = currentModule;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}

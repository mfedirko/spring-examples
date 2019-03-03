package com.example.core.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class RequestContextProcessor {
    public void process(HttpServletRequest request, HttpServletResponse response){
        RequestContextWrapper currentContext = RequestContextWrapper.getCurrentContext();
        currentContext.setRequest(request);
        currentContext.setResponse(response);
    }
}

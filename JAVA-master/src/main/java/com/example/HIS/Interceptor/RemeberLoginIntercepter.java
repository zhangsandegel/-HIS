package com.example.HIS.Interceptor;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class RemeberLoginIntercepter implements HandlerInterceptor {

//    @Autowired
//    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object userSession = session.getAttribute("patientInfo");//先查找session
        if(null==userSession){
            response.sendRedirect(request.getContextPath()+"/login.html");
            return false;
        }
        return true;
    }
}
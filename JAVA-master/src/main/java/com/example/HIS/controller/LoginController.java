package com.example.HIS.controller;

import com.alibaba.fastjson.JSON;
import com.example.HIS.models.Patient;
import com.example.HIS.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping("/login")
    @ResponseBody
    public String userLogin(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> parameter = new HashMap<>();
        String patientIdentity = request.getParameter("patientIdentity");
        String password = request.getParameter("password");
        Patient patient = loginService.selectPatient(patientIdentity);
        System.out.println("登录密码:"+password);
        if (null==patient){
            parameter.put("status", "null");
            parameter.put("msg","用户登录失败,无此用户");
            System.out.println("用户登录失败,无此用户");
            return JSON.toJSONString(parameter);
        }
        else if(!Objects.equals(patient.getPatientPassword(), password)) {
            System.out.println("密码:"+patient.getPatientPassword());
            parameter.put("status", "fail");
            parameter.put("msg","用户登录失败,密码错误");
            System.out.println("用户登录失败,密码错误");
            return JSON.toJSONString(parameter);
        }
        else{
            request.getSession().setAttribute("patientInfo", patient);
//            Cookie patientCookie = new Cookie("patientID",patient.getPatientIdentity());//服务器创建cookie,存储内容为用户名
//            patientCookie.setPath("/");
//            patientCookie.setMaxAge(60*60);
//            response.addCookie(patientCookie);
            parameter.put("status", "ok");
            parameter.put("msg","用户登录成功");
            parameter.put("data",patient.getPatientIdentity());
            System.out.println("用户登录成功");
            return JSON.toJSONString(parameter);
        }
    }
}

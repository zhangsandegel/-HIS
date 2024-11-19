package com.example.HIS.controller;

import com.alibaba.fastjson.JSON;
import com.example.HIS.service.RegisterService;
import com.example.HIS.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RegisterController {
    @Autowired
    RegisterService registerService;

//    @RequestMapping("/register")
//    public String register(String patientIdentity, String password1) {
//        Patient patient = new Patient(patientIdentity,password1);
//        int ans=0;
//        try
//        {
//             ans = registerService.addPatient(patient);
//        }
//        catch (Exception s){
//            System.out.println("注册失败");
//            return "redirect:register.html";
//        }
//        finally {
//            if(ans==1){
//                System.out.println("注册成功");
//                return "redirect:login.html";
//            }
//        }
//        return "";
//    }
    @RequestMapping("/register")
    @ResponseBody
    public  String userRegister(HttpServletRequest request, HttpSession session){
        Map<String, String> parameter = new HashMap<>();
        String patientIdentity = request.getParameter("patientIdentity");
        String password1 = request.getParameter("password1");
        String patientGender = request.getParameter("patientGender");
        String patientTel = request.getParameter("patientTel");
        String patientAddress = request.getParameter("patientAddress");
        Patient patient = registerService.selectPatient(patientIdentity);
        if (null==patient){
            Patient patient1 = new Patient(patientIdentity,password1);
            if (patientGender=="girl"){
                patient1.setPatientGender(0);
            }
            else{
                patient1.setPatientGender(1);
            }
            patient1.setPatientTel(patientTel);
            patient1.setPatientAddress(patientAddress);
            registerService.addPatient(patient1);
            session.setAttribute("patientInfo", patient1);
            parameter.put("status", "ok");
            System.out.println("用户注册成功");
            return JSON.toJSONString(parameter);
        }
        else {
            parameter.put("status", "no");
            System.out.println("用户存在,注册失败");
            return JSON.toJSONString(parameter);
        }
    }
}

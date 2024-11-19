package com.example.HIS.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.HIS.models.*;
import com.example.HIS.service.AppointmentServiceImpl;
import com.example.HIS.tool.DateTool;
import com.example.HIS.tool.DoctorRemain;
import com.example.HIS.tool.ReserveHistory;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AppointmentController {
    @Autowired
    private AppointmentServiceImpl appointmentService;

    //首页点击预约科室
    @RequestMapping("/appoint_dept")
    @ResponseBody
    public void appointDept(HttpServletRequest request, HttpServletResponse response)throws ParseException{
        addRemainNumberDaily();
        response.setContentType("text/html; charset=utf-8");
        List<RemainDeptNumber> remainDeptNumberList=appointmentService.getAllRemainDeptNumber();
        try{

            response.getWriter().print(new GsonBuilder().serializeNulls().create().toJson(remainDeptNumberList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //预约科室界面(appointment-dept.html)提交预约信息
    @RequestMapping("/appoint_dept_submit")
    @ResponseBody
    public String appointDeptSubmit(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        response.setContentType("text/html; charset=utf-8");
        Map<String, String> parameter = new HashMap<>();
        HttpSession session = request.getSession();
        Object userSession = session.getAttribute("patientInfo");//先查找session，获取患者信息
        Patient patient=(Patient)  userSession;
        //判断是否在黑名单中
        if (appointmentService.getBlackFlag(patient)==1){
            parameter.put("status", "fail");
            parameter.put("reson", "您近期违约记录较多，暂不能预约！");
            System.out.println("预约失败！");
            return JSON.toJSONString(parameter);
        }
        String departmentId = request.getParameter("departmentId");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date reserveTime =  sdf.parse(request.getParameter("reserveTime"));
        ReserveTable reserveTable=new ReserveTable();
        reserveTable.setPatientIdentity(patient.getPatientIdentity());
        reserveTable.setReserveTime(reserveTime);
        reserveTable.setDepartmentId(departmentId);
        reserveTable.setReserveType(1);
        reserveTable.setReserveState(0);
        reserveTable.setDoctorId("D0000001");
//        System.out.println(patient.getPatientIdentity());
        //更新剩余名额
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH");
        List<RemainDeptNumber> remainDeptNumbers=appointmentService.getRemainDeptNumberByDeptId(departmentId);
        RemainDeptNumber remainDeptNumber=new RemainDeptNumber();
        for (RemainDeptNumber remain:remainDeptNumbers){
            if(sdf1.format(remain.getTime()).equals(sdf1.format(reserveTime))){
                remainDeptNumber.setRemainDeptNumberId(remain.getRemainDeptNumberId());
                remainDeptNumber.setDepartmentId(remain.getDepartmentId());
                remainDeptNumber.setTime(remain.getTime());
                remainDeptNumber.setNumber(remain.getNumber()-1);
                //余额充足
                if (remainDeptNumber.getNumber()>=0){
                    System.out.println(remainDeptNumber.getRemainDeptNumberId()+remainDeptNumber.getDepartmentId()+remainDeptNumber.getTime());
                    appointmentService.updateRemainDeptNumber(remainDeptNumber);
                    //新增预约记录
                    appointmentService.addReserveTable(reserveTable);
                    session.setAttribute("reserveTableInfo", reserveTable);
                    parameter.put("status", "ok");
                    System.out.println("预约成功！");
                    return JSON.toJSONString(parameter);
                }
            }
        }
        parameter.put("status", "fail");
        parameter.put("reson", "当前时间已约满，请重新选择预约时间！");
        System.out.println("预约失败！");
        return JSON.toJSONString(parameter);
    }

    ////首页点击预约专家
    @RequestMapping("/appoint_doctor")
    @ResponseBody
    public void appointDoctor(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        addRemainNumberDaily();
        response.setContentType("text/html; charset=utf-8");
        List<DoctorRemain> remainDoctorNumberList=appointmentService.getAllDoctorRemain();
        try{
            response.getWriter().print(new GsonBuilder().serializeNulls().create().toJson(remainDoctorNumberList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////预约专家界面(appointment-doctor.html)提交预约信息
    @RequestMapping("/appoint_doctor_submit")
    @ResponseBody
    public String appointDoctorSubmit(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        response.setContentType("text/html; charset=utf-8");
        Map<String, String> parameter = new HashMap<>();
        HttpSession session = request.getSession();
        Object userSession = session.getAttribute("patientInfo");//先查找session，获取患者信息
        Patient patient=(Patient)  userSession;
        //判断是否在黑名单中
        if (appointmentService.getBlackFlag(patient)==1){
            parameter.put("status", "fail");
            parameter.put("reson", "您近期违约记录较多，暂不能预约！");
            System.out.println("预约失败！");
            return JSON.toJSONString(parameter);
        }
        String doctorId = request.getParameter("doctorId");
        String departmentId = request.getParameter("departmentId");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date reserveTime =  sdf.parse(request.getParameter("reserveTime"));
        ReserveTable reserveTable=new ReserveTable();
        reserveTable.setPatientIdentity(patient.getPatientIdentity());
        reserveTable.setReserveTime(reserveTime);
        reserveTable.setDepartmentId(departmentId);
        reserveTable.setReserveType(0);//专家号
        reserveTable.setReserveState(0);//待确认
        reserveTable.setDoctorId(doctorId);
//        System.out.println(patient.getPatientIdentity());
        //更新剩余名额
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH");
        List<RemainDoctorNumber> remainDoctorNumbers=appointmentService.getRemainDeptNumberByDoctorId(doctorId);
        RemainDoctorNumber remainDoctorNumber=new RemainDoctorNumber();
        for (RemainDoctorNumber remain:remainDoctorNumbers){
            if(sdf1.format(remain.getTime()).equals(sdf1.format(reserveTime))){
                remainDoctorNumber.setRemainDoctorNumberId(remain.getRemainDoctorNumberId());
                remainDoctorNumber.setDoctorId(remain.getDoctorId());;
                remainDoctorNumber.setTime(remain.getTime());
                remainDoctorNumber.setNumber(remain.getNumber()-1);
                //余额充足
                if (remainDoctorNumber.getNumber()>=0){
                    System.out.println(remainDoctorNumber.getRemainDoctorNumberId()+remainDoctorNumber.getDoctorId()+remainDoctorNumber.getTime());
                    appointmentService.updateRemainDoctorNumber(remainDoctorNumber);
                    //新增预约记录
                    appointmentService.addReserveTable(reserveTable);
                    session.setAttribute("reserveTableInfo", reserveTable);
                    parameter.put("status", "ok");
                    System.out.println("预约成功！");
                    return JSON.toJSONString(parameter);
                }
            }
        }
        parameter.put("status", "fail");
        parameter.put("reson", "当前时间已约满，请重新选择预约时间！");
        System.out.println("预约失败！");
        return JSON.toJSONString(parameter);
    }

    //首页点击”我的预约记录“
    @RequestMapping("/appoint_history")
    @ResponseBody
    public void appointHistory(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html; charset=utf-8");
        HttpSession session = request.getSession();
        Object userSession = session.getAttribute("patientInfo");//先查找session，获取患者信息
        Patient patient=(Patient)  userSession;
        List<ReserveHistory> reserveTableList=appointmentService.getReserveHistoryByPatientId(patient.getPatientIdentity());
        try{
            response.getWriter().print(new GsonBuilder().serializeNulls().create().toJson(reserveTableList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //取消预约
    @RequestMapping("/appoint_cancel")
    @ResponseBody
    public String appointCancel(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html; charset=utf-8");
        Map<String, String> parameter = new HashMap<>();
        HttpSession session = request.getSession();
        Object userSession = session.getAttribute("patientInfo");//先查找session，获取患者信息
        Patient patient=(Patient)  userSession;
        int reserveId = Integer.parseInt(request.getParameter("reserveId"));
        ReserveTable reserveTable=appointmentService.getRserveTabltByPk(reserveId);
        reserveTable.setReserveState(-1);
        //更新预约单信息-->已取消
        appointmentService.updateReserveState(reserveTable);
        //添加违约记录
        ViolateTable violateTable=new ViolateTable();
        violateTable.setReserveId(reserveId);
        appointmentService.addViolateTable(violateTable);
        //查询是否更新黑名单状态
        if (appointmentService.getBlackFlag(patient)==1){
            parameter.put("status", "ok");
            parameter.put("msg", "您近期违约记录较多，现已加入黑名单。");
            System.out.println("取消成功！加入黑名单");
            return JSON.toJSONString(parameter);
        }
        parameter.put("status", "ok");
        System.out.println("取消成功！");
        return JSON.toJSONString(parameter);
    }


    public void addRemainNumberDaily(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String result = format.format(today);
        if (result.equals("08:00:00")){//每天8点更新
            for (int i=9;i<17;i++){
                Date day=DateTool.getFutureDate(6,i); //获取六天后各时间点
                RemainDeptNumber remainDeptNumber=new RemainDeptNumber();
                List<Department> departments=appointmentService.getAllDepartment();
                for (Department dept:departments){
                    remainDeptNumber.setDepartmentId(dept.getDepartmentId());
                    remainDeptNumber.setTime(day);
                    remainDeptNumber.setNumber(20);
                    appointmentService.addRemainDeptNumber(remainDeptNumber);
                }
                RemainDoctorNumber remainDoctorNumber=new RemainDoctorNumber();
                List<Doctor> doctors=appointmentService.getAllDoctor();
                for (Doctor doctor:doctors){
                    remainDoctorNumber.setDoctorId(doctor.getDoctorId());
                    remainDoctorNumber.setTime(day);
                    remainDoctorNumber.setNumber(5);
                    appointmentService.addremainDoctorNumber(remainDoctorNumber);
                }
            }
        }
    }


    //一次性新增预约名额-->数据库初始化
    public void addRemainNumber() throws ParseException {
//        System.out.println("addRemainNumber():");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int day=1;day<13;day++){
            for (int i=9;i<17;i++){
                String day_string;
                if (i<10){
                    day_string="2022-01-"+String.valueOf(day)+" 0"+String.valueOf(i)+":00:00";
                }
                else{
                    day_string="2022-01-"+String.valueOf(day)+" "+String.valueOf(i)+":00:00";
                }
                Date date=format.parse(day_string);
                RemainDeptNumber remainDeptNumber=new RemainDeptNumber();
                List<Department> departments=appointmentService.getAllDepartment();
                for (Department dept:departments){
//                    System.out.println(dept.getDepartmentId());

                    remainDeptNumber.setDepartmentId(dept.getDepartmentId());
                    remainDeptNumber.setTime(date);
                    remainDeptNumber.setNumber(20);
                    appointmentService.addRemainDeptNumber(remainDeptNumber);

                }
                RemainDoctorNumber remainDoctorNumber=new RemainDoctorNumber();
                List<Doctor> doctors=appointmentService.getAllDoctor();
                for (Doctor doctor:doctors){
//                    System.out.println(doctor.getDoctorName());
                    remainDoctorNumber.setDoctorId(doctor.getDoctorId());
                    remainDoctorNumber.setTime(date);
                    remainDoctorNumber.setNumber(5);
                    appointmentService.addremainDoctorNumber(remainDoctorNumber);
                }
            }
        }
    }
}

package com.example.HIS.service;

import com.example.HIS.models.*;
import com.example.HIS.tool.DoctorRemain;
import com.example.HIS.tool.ReserveHistory;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AppointmentService {
    List<RemainDeptNumber> getAllRemainDeptNumber();

    List<DoctorRemain> getAllDoctorRemain();
    int addReserveTable(ReserveTable reserveTable);
    int getBlackFlag(Patient patient);
    int updateRemainDeptNumber(RemainDeptNumber remainDeptNumber);
    List<RemainDeptNumber> getRemainDeptNumberByDeptId( String DeptId);

    int addRemainDeptNumber(RemainDeptNumber remainDeptNumber);
    List<Department> getAllDepartment();

    List<Doctor> getAllDoctor();

    void addremainDoctorNumber(RemainDoctorNumber remainDoctorNumber);

    List<RemainDoctorNumber> getRemainDeptNumberByDoctorId(String doctorId);

    void updateRemainDoctorNumber(RemainDoctorNumber remainDoctorNumber);

    List<ReserveHistory> getReserveHistoryByPatientId(String patientIdentity);

    void updateReserveState(ReserveTable reserveTable);

    ReserveTable getRserveTabltByPk(int reserveId);

    void addViolateTable(ViolateTable violateTable);
}

package com.example.HIS.models;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * take_medine_tables
 * @author 
 */
@Data
public class TakeMedineTable implements Serializable {
    /**
     * 取药单号
     */
    private Integer takeId;

    /**
     * 患者身份证号
     */
    private String patientIdentity;

    /**
     * 药剂师工号
     */
    private String pharmacistId;

    /**
     * 药品编号
     */
    private String medicineId;

    /**
     * 药品数量
     */
    private Integer takeNumber;

    /**
     * 取药状态,0待配药,1配药中,2待取药,3已取药
     */
    private Integer takeState;

    /**
     * 开具时间
     */
    private Date billTime;

    /**
     * 取药时间
     */
    private Date takeTime;

    private static final long serialVersionUID = 1L;

    public TakeMedineTable() {
    }

    public Integer getTakeId() {
        return takeId;
    }

    public void setTakeId(Integer takeId) {
        this.takeId = takeId;
    }

    public String getPatientIdentity() {
        return patientIdentity;
    }

    public void setPatientIdentity(String patientIdentity) {
        this.patientIdentity = patientIdentity;
    }

    public String getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(String pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public Integer getTakeNumber() {
        return takeNumber;
    }

    public void setTakeNumber(Integer takeNumber) {
        this.takeNumber = takeNumber;
    }

    public Integer getTakeState() {
        return takeState;
    }

    public void setTakeState(Integer takeState) {
        this.takeState = takeState;
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }
}
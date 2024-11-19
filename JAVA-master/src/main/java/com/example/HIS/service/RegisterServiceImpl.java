package com.example.HIS.service;

import com.example.HIS.generate.PatientDao;
import com.example.HIS.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    PatientDao patientDao;

    @Override
    public int addPatient(Patient patient) {
        return patientDao.insertSelective(patient);
    }

    @Override
    public Patient selectPatient(String patientIdentity) {
        return patientDao.selectByPrimaryKey(patientIdentity);
    }
}

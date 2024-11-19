package com.example.HIS.service;

import com.example.HIS.generate.PatientDao;
import com.example.HIS.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    PatientDao patientDao;

    @Override
    public int login(String patientIdentity, String password) {
        return 0;
    }

    @Override
    public Patient selectPatient(String patientIdentity) {
        return patientDao.selectByPrimaryKey(patientIdentity);
    }
}

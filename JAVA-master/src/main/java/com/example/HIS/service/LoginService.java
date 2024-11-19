package com.example.HIS.service;

import com.example.HIS.models.Patient;

public interface LoginService {
    int login(String patientIdentity,String password);
    Patient selectPatient(String patientIdentity);
}

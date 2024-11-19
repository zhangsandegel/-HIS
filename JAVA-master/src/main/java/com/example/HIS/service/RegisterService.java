package com.example.HIS.service;

import com.example.HIS.models.Patient;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RegisterService {
    int addPatient(Patient patient);

    Patient selectPatient(String patientIdentity);
}

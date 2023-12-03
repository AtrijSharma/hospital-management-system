package com.hospitalManagement.staff.services.patientService;

import com.hospitalManagement.staff.entity.Patient;

import java.util.List;

public interface PatientService {
    void admitPatient(Patient patient);
    List<Patient> getAllPatients();
    void dischargePatient(Long patientId);
}
package com.hospitalManagement.staff.services.patientService.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hospitalManagement.staff.entity.Patient;
import com.hospitalManagement.staff.repositories.PatientRepository;
import com.hospitalManagement.staff.services.patientService.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void admitPatient(Patient patient) {
        logger.info("Admitting patient: {}", patient.getName());
        patientRepository.save(patient);
        logger.info("Patient admitted successfully: {}", patient.getName());
    }

    @Override
    public List<Patient> getAllPatients() {
        logger.info("Fetching all patients");
        List<Patient> patients = patientRepository.findAll();
        logger.info("Fetched {} patients", patients.size());
        return patients;
    }

    @Override
    public void dischargePatient(Long patientId) {
        logger.info("Discharging patient with ID: {}", patientId);
        patientRepository.deleteById(patientId);
        logger.info("Patient discharged successfully with ID: {}", patientId);
    }
}

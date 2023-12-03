package com.hospitalManagement.staff.repositories;

import com.hospitalManagement.staff.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAll();
}

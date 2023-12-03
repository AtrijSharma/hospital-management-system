package com.hospitalManagement.staff.repositories;

import com.hospitalManagement.staff.entity.HospitalStaff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalStaffRepository extends JpaRepository<HospitalStaff, Long> {
    Optional<HospitalStaff> findByUsername(String username);
    boolean existsByUsernameAndType(String username, String type);

}

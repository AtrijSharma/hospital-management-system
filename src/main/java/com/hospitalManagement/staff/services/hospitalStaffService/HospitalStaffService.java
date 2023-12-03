package com.hospitalManagement.staff.services.hospitalStaffService;

import com.hospitalManagement.staff.entity.HospitalStaff;
import org.springframework.security.core.userdetails.UserDetails;

public interface HospitalStaffService {
    boolean signup(HospitalStaff hospitalStaff, String type);
    UserDetails loadUserByUsername(String username);
}
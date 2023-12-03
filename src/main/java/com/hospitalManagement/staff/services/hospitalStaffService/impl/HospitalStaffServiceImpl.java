package com.hospitalManagement.staff.services.hospitalStaffService.impl;

import com.hospitalManagement.staff.entity.HospitalStaff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hospitalManagement.staff.repositories.HospitalStaffRepository;
import com.hospitalManagement.staff.security.CustomUserDetails;
import com.hospitalManagement.staff.services.hospitalStaffService.HospitalStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("hospitalStaffServiceImpl")
public class HospitalStaffServiceImpl implements HospitalStaffService {
    private static final Logger logger = LoggerFactory.getLogger(HospitalStaffServiceImpl.class);

    @Autowired
    private HospitalStaffRepository hospitalStaffRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean signup(HospitalStaff hospitalStaff, String type) {
        // Check if a user with the same username and type already exists
        if (userExists(hospitalStaff.getUsername(), type)) {
            logger.warn("User with the same username '{}' and type '{}' already exists.", hospitalStaff.getUsername(), type);
            return false; // User already exists
        }

        hospitalStaff.setType(type);
        hospitalStaff.setPassword(passwordEncoder.encode(hospitalStaff.getPassword()));
        hospitalStaffRepository.save(hospitalStaff);
        logger.info("Hospital staff signed up successfully: {}", hospitalStaff.getUsername());
        return true; // Signup successful
    }

    // Additional method to check if a user with the same username and type already exists
    private boolean userExists(String username, String type) {
        return hospitalStaffRepository.existsByUsernameAndType(username, type);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user by username: {}", username);
        HospitalStaff hospitalStaff = hospitalStaffRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Fetch authorities based on user type
        List<GrantedAuthority> authorities = getAuthoritiesByType(hospitalStaff.getType());

        logger.info("User loaded successfully: {}", username);
        // Create a UserDetails object
        return new CustomUserDetails(hospitalStaff.getUsername(), hospitalStaff.getPassword(), authorities);
    }
    // Additional method to fetch authorities based on user type
    private List<GrantedAuthority> getAuthoritiesByType(String userType) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Implement your logic to map user types to roles/authorities
        switch (userType) {
            case "doctor":
                authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
                break;
            case "nurse":
                authorities.add(new SimpleGrantedAuthority("ROLE_NURSE"));
                break;
            case "administrator":
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            // Add more cases as needed

            default:
                // Default role for unknown user types
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authorities;
    }
}

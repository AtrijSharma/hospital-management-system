package com.hospitalManagement.staff.security;
import com.hospitalManagement.staff.entity.HospitalStaff;
import com.hospitalManagement.staff.repositories.HospitalStaffRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final HospitalStaffRepository hospitalStaffRepository;

    public CustomUserDetailsService(HospitalStaffRepository hospitalStaffRepository) {
        this.hospitalStaffRepository = hospitalStaffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<HospitalStaff> optionalUser = hospitalStaffRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            HospitalStaff hospitalStaff = optionalUser.get();
            return new org.springframework.security.core.userdetails.User(
                    hospitalStaff.getUsername(),
                    hospitalStaff.getPassword(),
                    Collections.emptyList() // You may provide roles/authorities here if needed
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}

package com.hospitalManagement.staff;
import com.hospitalManagement.staff.entity.HospitalStaff;
import com.hospitalManagement.staff.repositories.HospitalStaffRepository;
import com.hospitalManagement.staff.services.hospitalStaffService.impl.HospitalStaffServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class HospitalStaffServiceImplTest {

    @Mock
    private HospitalStaffRepository hospitalStaffRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private HospitalStaffServiceImpl hospitalStaffService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSignup() {
        HospitalStaff hospitalStaff = new HospitalStaff();
        hospitalStaff.setUsername("testuser");
        hospitalStaff.setPassword("testpassword");
        hospitalStaff.setType("doctor");

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(hospitalStaffRepository.save(any())).thenReturn(hospitalStaff);

        hospitalStaffService.signup(hospitalStaff, "doctor");

        assertEquals("encodedPassword", hospitalStaff.getPassword());
    }

    @Test
    void testLoadUserByUsername() {
        HospitalStaff hospitalStaff = new HospitalStaff();
        hospitalStaff.setUsername("testuser");
        hospitalStaff.setPassword("testpassword");
        hospitalStaff.setType("doctor");

        when(hospitalStaffRepository.findByUsername("testuser")).thenReturn(Optional.of(hospitalStaff));

        UserDetails userDetails = hospitalStaffService.loadUserByUsername("testuser");

        assertEquals("testuser", userDetails.getUsername());
        assertEquals("testpassword", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(hospitalStaffRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> hospitalStaffService.loadUserByUsername("nonexistentuser"));
    }
}

package com.hospitalManagement.staff.controller;

import com.hospitalManagement.staff.entity.HospitalStaff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hospitalManagement.staff.entity.Patient;
import com.hospitalManagement.staff.services.hospitalStaffService.HospitalStaffService;
import com.hospitalManagement.staff.services.patientService.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalManagementController {
    private static final Logger logger = LoggerFactory.getLogger(HospitalManagementController.class);

    @Autowired
    private HospitalStaffService hospitalStaffService;

    @Autowired
    private PatientService patientService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(@RequestBody HospitalStaff hospitalStaff, @RequestParam String type) {
        logger.info("Received request for signup: {}", hospitalStaff.getUsername());
        boolean signupSuccessful = hospitalStaffService.signup(hospitalStaff, type);

        if (signupSuccessful) {
            logger.info("Hospital staff signed up successfully: {}", hospitalStaff.getUsername());
            return new ResponseEntity<>("Hospital staff signed up successfully!", HttpStatus.CREATED);
        } else {
            logger.info("User already exists with the same username and type: {}", hospitalStaff.getUsername());
            return new ResponseEntity<>("User already exists with the same username and type.", HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            logger.info("Attempting to authenticate user: {}", username);
            // Create an authentication token with the provided credentials
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Set the authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("User authenticated successfully: {}", username);
            return new ResponseEntity<>("Login successful!", HttpStatus.OK);
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", username, e);
            // Authentication failed
            return new ResponseEntity<>("Login failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/patients/admit")
    public ResponseEntity<String> admitPatient(@RequestBody Patient patient) {
        logger.info("Received request to admit patient: {}", patient.getName());
        patientService.admitPatient(patient);
        logger.info("Patient admitted successfully: {}", patient.getName());
        return new ResponseEntity<>("Patient admitted successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/patients/all")
    public ResponseEntity<List<Patient>> getAllPatients() {
        logger.info("Received request to get all patients");
        List<Patient> patients = patientService.getAllPatients();
        logger.info("Returning the list of all patients");
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PostMapping("/patients/discharge/{patientId}")
    public ResponseEntity<String> dischargePatient(@PathVariable Long patientId) {
        logger.info("Received request to discharge patient with ID: {}", patientId);
        patientService.dischargePatient(patientId);
        logger.info("Patient discharged successfully with ID: {}", patientId);
        return new ResponseEntity<>("Patient discharged successfully!", HttpStatus.OK);
    }

    @GetMapping("/auth/current-user")
    public ResponseEntity<String> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            logger.info("Request to get current user. Current user: {}", username);
            return new ResponseEntity<>("Current user: " + username, HttpStatus.OK);
        } else {
            // Handle the case where the principal is not a UserDetails
            logger.warn("Unable to determine the current user");
            return new ResponseEntity<>("Unable to determine the current user", HttpStatus.UNAUTHORIZED);
        }
    }
}

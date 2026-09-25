package com.manoj.medcore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HospitalNotFoundException.class)
    public ResponseEntity<String> handleHospitalNotFound(
            HospitalNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler(DepartmentNotFoundException.class)
public ResponseEntity<String> handleDepartmentNotFound(
        DepartmentNotFoundException ex) {

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
}

@ExceptionHandler(PatientNotFoundException.class)
public ResponseEntity<String> handlePatientNotFound(
        PatientNotFoundException ex) {

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
}

@ExceptionHandler(AppointmentNotFoundException.class)
public ResponseEntity<String> handleAppointmentNotFound(
        AppointmentNotFoundException ex) {

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
}

@ExceptionHandler(MedicalRecordNotFoundException.class)
public ResponseEntity<String> handleMedicalRecordNotFound(
        MedicalRecordNotFoundException ex) {

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
}

@ExceptionHandler(PrescriptionNotFoundException.class)
public ResponseEntity<String> handlePrescriptionNotFound(
        PrescriptionNotFoundException ex) {

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
}
}
package com.manoj.medcore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manoj.medcore.dto.AppointmentRequestDTO;
import com.manoj.medcore.dto.AppointmentResponseDTO;
import com.manoj.medcore.dto.AppointmentUpdateDTO;
import com.manoj.medcore.exception.AppointmentNotFoundException;
import com.manoj.medcore.exception.DoctorNotFoundException;
import com.manoj.medcore.exception.PatientNotFoundException;
import com.manoj.medcore.model.Appointment;
import com.manoj.medcore.model.Doctor;
import com.manoj.medcore.model.Patient;
import com.manoj.medcore.repository.AppointmentRepository;
import com.manoj.medcore.repository.DoctorRepository;
import com.manoj.medcore.repository.PatientRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {

        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public AppointmentResponseDTO createAppointment(
            AppointmentRequestDTO requestDTO) {

        Patient patient =
                patientRepository.findById(requestDTO.getPatientId())
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found with id: "
                                                + requestDTO.getPatientId()));

        Doctor doctor =
                doctorRepository.findById(requestDTO.getDoctorId())
                        .orElseThrow(() ->
                                new DoctorNotFoundException(
                                        "Doctor not found with id: "
                                                + requestDTO.getDoctorId()));

        Appointment appointment = new Appointment();

        appointment.setAppointmentDateTime(
                requestDTO.getAppointmentDateTime());

        appointment.setStatus("SCHEDULED");

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment savedAppointment =
                appointmentRepository.save(appointment);

        return mapToResponse(savedAppointment);
    }

    public List<AppointmentResponseDTO> getAllAppointments() {

        List<Appointment> appointments =
                appointmentRepository.findAll();

        List<AppointmentResponseDTO> responseList =
                new ArrayList<>();

        for (Appointment appointment : appointments) {
            responseList.add(mapToResponse(appointment));
        }

        return responseList;
    }

    public AppointmentResponseDTO getAppointmentById(Long id) {

        Appointment appointment =
                appointmentRepository.findById(id)
                        .orElseThrow(() ->
                                new AppointmentNotFoundException(
                                        "Appointment not found with id: "
                                                + id));

        return mapToResponse(appointment);
    }

    public AppointmentResponseDTO updateAppointment(
            Long id,
            AppointmentUpdateDTO updateDTO) {

        Appointment appointment =
                appointmentRepository.findById(id)
                        .orElseThrow(() ->
                                new AppointmentNotFoundException(
                                        "Appointment not found with id: "
                                                + id));

        Patient patient =
                patientRepository.findById(updateDTO.getPatientId())
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found with id: "
                                                + updateDTO.getPatientId()));

        Doctor doctor =
                doctorRepository.findById(updateDTO.getDoctorId())
                        .orElseThrow(() ->
                                new DoctorNotFoundException(
                                        "Doctor not found with id: "
                                                + updateDTO.getDoctorId()));

        appointment.setAppointmentDateTime(
                updateDTO.getAppointmentDateTime());

        appointment.setStatus(updateDTO.getStatus());

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment updatedAppointment =
                appointmentRepository.save(appointment);

        return mapToResponse(updatedAppointment);
    }

    public void deleteAppointment(Long id) {

        Appointment appointment =
                appointmentRepository.findById(id)
                        .orElseThrow(() ->
                                new AppointmentNotFoundException(
                                        "Appointment not found with id: "
                                                + id));

        appointmentRepository.delete(appointment);
    }

    private AppointmentResponseDTO mapToResponse(
            Appointment appointment) {

        AppointmentResponseDTO responseDTO =
                new AppointmentResponseDTO();

        responseDTO.setId(appointment.getId());

        responseDTO.setAppointmentDateTime(
                appointment.getAppointmentDateTime());

        responseDTO.setStatus(
                appointment.getStatus());

        responseDTO.setPatientId(
                appointment.getPatient().getId());

        responseDTO.setDoctorId(
                appointment.getDoctor().getId());

        return responseDTO;
    }
}
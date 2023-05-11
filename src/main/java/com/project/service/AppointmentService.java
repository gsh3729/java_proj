package com.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.model.Appointment;
import com.project.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EmailService emailService;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    } //.orElse(null)

    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        // Check if there are any overlapping appointments
        List<Appointment> overlappingAppointments = appointmentRepository.findByStartLessThanEqualAndEndGreaterThanEqual(end, start);
        return overlappingAppointments.isEmpty();
    }

    public Appointment createAppointment(Appointment appointment) {
        CompletableFuture.runAsync(() -> emailService.sendAppointmentConfirmation(appointment));
        return appointmentRepository.save(appointment);
    }

    // Other methods for updating and deleting appointments

}

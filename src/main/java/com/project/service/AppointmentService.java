package com.project.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.model.Appointment;
import com.project.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        // Check if there are any overlapping appointments
        List<Appointment> overlappingAppointments = appointmentRepository.findByStartLessThanEqualAndEndGreaterThanEqual(end, start);
        return overlappingAppointments.isEmpty();
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // Other methods for updating and deleting appointments

}

package com.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.project.model.User;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.model.Appointment;
import com.project.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentByUserId(Long id) {
        return appointmentRepository.findByUserId(id);
    }
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    } //.orElse(null)

    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        List<Appointment> overlappingAppointments = appointmentRepository.findByStartLessThanEqualAndEndGreaterThanEqual(end, start);
        return overlappingAppointments.isEmpty();
    }

    public Appointment createAppointment(Appointment appointment) {
        User user = userRepository.findById(appointment.getUserId());
        CompletableFuture.runAsync(() -> emailService.sendAppointmentConfirmation(appointment, user));
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getFilteredAppointments(LocalDateTime start, LocalDateTime end) {
        List<Appointment> appointments = appointmentRepository.findByStartAndEnd(start, end);
        return appointments;
    }

    public List<Appointment> getUserAppointments() {
        User user = userService.getCurrentUser();
        return getAppointmentByUserId(user.getUserId());
    }
}

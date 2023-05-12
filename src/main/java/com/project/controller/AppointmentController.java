package com.project.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;


import com.project.model.Appointment;
import com.project.service.AppointmentService;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

//    @GetMapping("/")
    @RequestMapping("/")
    public String index()
    {
//returns to index.html
        return "index";
    }

    @GetMapping("/har")
    public ResponseEntity<Integer> home() {
        return new ResponseEntity<>(5, HttpStatus.OK);
    }


//    @GetMapping("/appointments")
//    public ResponseEntity<List<Appointment>> getAllAppointments() {
//        List<Appointment> appointments = appointmentService.getAllAppointments();
//        return new ResponseEntity<>(appointments, HttpStatus.OK);
//    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping("/appointments")
    public String getAppointmentById(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
//        return new ResponseEntity<>(appointment, HttpStatus.OK);
        for (int i=0; i<appointments.size();i++) {
            System.out.println("Appointments: " + appointments.get(i));
        }
        model.addAttribute("appointments", appointments);
        return "mybookings";
    }


    @GetMapping("/user_appointments/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentByUserId(@PathVariable Long id) {
        List<Appointment> appointments = appointmentService.getAppointmentByUserId(id);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PostMapping("/appointments")
    public ResponseEntity<Appointment> createAppointment(@ModelAttribute Appointment appointment, Model model) {
        System.out.print("Appointment: " + appointment);
        LocalDateTime start = appointment.getStart();
        LocalDateTime end = appointment.getEnd();

        // Check if the appointment is available
        if (appointmentService.isAvailable(start, end)) {
            Appointment createdAppointment = appointmentService.createAppointment(appointment);
            return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}

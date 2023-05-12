package com.project.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.project.model.User;
import jakarta.servlet.http.HttpSession;
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
//    @RequestMapping("/")
//    public String index()
//    {
////returns to index.html
//        return "index";
//    }

    @GetMapping("/")
    public String main(HttpSession session, Model model) {
        // Check if user is logged in
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // User is not logged in, redirect to login page
            return "redirect:/login";
        }

//        // Add user id to model for further use
//        model.addAttribute("userId", userId);
//
//        // Load appointments for the user and add to model
//        List<Appointment> appointments = appointmentService.getAppointmentsByUserId(userId);
//        model.addAttribute("appointments", appointments);

        // Render main page
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


//    @GetMapping("/user_appointments/{id}")
//    public ResponseEntity<List<Appointment>> getAppointmentByUserId(@PathVariable Long id) {
//        List<Appointment> appointments = appointmentService.getAppointmentByUserId(id);
//        return new ResponseEntity<>(appointments, HttpStatus.OK);
//    }

    @GetMapping("/my_appointments")
    public ResponseEntity<List<Appointment>> getUserAppointment() {
        List<Appointment> appointments = appointmentService.getUserAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/availability")
    public String getAvailability(@RequestParam("start") String start, @RequestParam("end") String end, Model model) {
        System.out.println("Start: "+ start);
        System.out.println("End: "+ end);
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);


        List<Appointment> appointments = appointmentService.getFilteredAppointments(startTime, endTime);
        for (int i=0; i<appointments.size();i++) {
            System.out.println("Appointments: " + appointments.get(i));
        }
        model.addAttribute("appointments", appointments);
        return "availability";
    }

    @PostMapping("/appointments")
    public String createAppointment(@ModelAttribute Appointment appointment, Model model) {
        System.out.print("Appointment: " + appointment);
        LocalDateTime start = appointment.getStart();
        LocalDateTime end = appointment.getEnd();

        // Check if the appointment is available
        if (appointmentService.isAvailable(start, end)) {
            Appointment createdAppointment = appointmentService.createAppointment(appointment);
//            return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
            return "goodresult";
        } else {
            return "badresult";
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}

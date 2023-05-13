package com.project.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import com.project.model.Appointment;
import com.project.service.AppointmentService;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String main(HttpSession session, Model model) {
        Long userId = null;
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj instanceof Integer) {
            Integer userIdInt = (Integer) userIdObj;
            userId = userIdInt.longValue();
        } else if (userIdObj instanceof Long) {
            userId = (Long) userIdObj;
        }
        if (userId == null) {
            return "redirect:/login";
        }

        return "index";
    }



    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping("/appointments")
    public String getAppointmentById(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        for (int i=0; i<appointments.size();i++) {
            System.out.println("Appointments: " + appointments.get(i));
        }
        model.addAttribute("appointments", appointments);
        return "mybookings";
    }


    @GetMapping("/my_appointments")
    public String getUserAppointment(Model model) {
        List<Appointment> appointments = appointmentService.getUserAppointments();
        model.addAttribute("appointments", appointments);
        return "mybookings";
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

        appointment.setUserId(userService.getCurrentUser().getUserId());

        if (appointmentService.isAvailable(start, end)) {
            Appointment createdAppointment = appointmentService.createAppointment(appointment);
            return "goodresult";
        } else {
            return "badresult";
        }
    }
}

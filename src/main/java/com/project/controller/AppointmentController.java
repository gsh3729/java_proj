package com.project.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.Appointment;
import com.project.service.AppointmentService;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/har")
    public ResponseEntity<Integer> home() {
        return new ResponseEntity<>(5, HttpStatus.OK);
    }


    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PostMapping("/appointments")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
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

    // Other methods for updating and deleting appointments

}

//
//@RestController
//@RequestMapping("/appointments")
//public class AppointmentController {
//    private Map<LocalDate, List<AppointmentAvailability>> availabilityMap = new HashMap<>();
//
//    @PostMapping
//    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment) {
//        // Check if the requested time slot is available
//        List<AppointmentAvailability> availabilities = availabilityMap.computeIfAbsent(appointment.getStartTime().toLocalDate(), k -> new ArrayList<>());
//        boolean available = true;
//        for (AppointmentAvailability availability : availabilities) {
//            if (appointment.getStartTime().isBefore(availability.getEndTime()) && appointment.getEndTime().isAfter(availability.getStartTime())) {
//                available = false;
//                break;
//            }
//        }
//
//        // Book the appointment if it's available, or return an error message
//        if (available) {
//            availabilities.add(new AppointmentAvailability(appointment.getStartTime(), appointment.getEndTime(), false));
//            return ResponseEntity.ok("Appointment booked successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The requested time slot is not available");
//        }
//    }
//}

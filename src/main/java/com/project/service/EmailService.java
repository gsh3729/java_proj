package com.project.service;


import com.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;


import com.project.model.Appointment;



@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendAppointmentConfirmation(Appointment appointment, User user) {
        // Construct the email message
        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(user.getEmail());
        message.setTo("sg7372@nyu.edu");
        message.setSubject("Appointment Confirmation");
        message.setText(String.format("Your appointment %s on %s at %s has been confirmed.",
                appointment.getName(), appointment.getStart(), appointment.getLocation()));

        // Send the email
        javaMailSender.send(message);
    }
}


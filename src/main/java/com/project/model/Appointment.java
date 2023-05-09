package com.project.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Appointment {
    private Integer appointmentId;
    private String appointmentName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer userId;
    private String location;
}

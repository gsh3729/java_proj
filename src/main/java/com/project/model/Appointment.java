package com.project.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Appointment {
    private Long id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long userId;
    private String location;
}

package com.project.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Appointment {
    private Integer id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private Integer userId;
    private String location;


}

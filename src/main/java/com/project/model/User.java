package com.project.model;

import lombok.Data;

@Data
public class User {
    private Long userId;
    private String name;
    private String email;
    private String password;
}

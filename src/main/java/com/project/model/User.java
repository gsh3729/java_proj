package com.project.model;

import lombok.Data;

@Data
public class User {
    private Integer userId;
    private String name;
    private String email;
    private String password;
}

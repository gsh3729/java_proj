# Calendar And Appointment Management System 

## Sriharsha Gaddipati(sg7372), Sai Rajeev Koppuravuri(rk4305)

## Spring Web Application

Spring MVC Architecture (Model View Controller)

Controllers - Appointment Controller, Login Controller, Sign up Controller

Model - Appointment, User

Services - Appointment Service, User Service, Email Service

Repository(Data Access Layer) - AppointmentRepository, UserRepository 

## Features

Multi user application

Users can signup, login, and logout

Users can create appointments. 

If there are any conflicts with appointments made by other users, it throws errors saying that booking can't be done. 

Users can view the appointment bookings made by him/her. 

Users will receive email confirmation for the appointment booking. It is done using asynchronous processing. 

Users can see the bookings happened overall based on the selected date time.  

# Setup

## Mysql Setup

First install mysql on the system. Use this guide for the installation. https://dev.mysql.com/doc/mysql-installation-excerpt/8.0/en/macos-installation-pkg.html 

Thereafter, install MysqlWorkBench using https://dev.mysql.com/downloads/workbench/ 

Create a new schema named calendar on the mysqlworkbench to store our tables. Now, run the below two statements to create users and appointments tables.

```
CREATE TABLE calendar.Users (
  user_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(20) NOT NULL,
  PRIMARY KEY (user_id)
);
```

```
CREATE TABLE calendar.Appointments (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL, 
  user_id INT NOT NULL,
  start_t DATETIME NOT NULL,
  end_t DATETIME NOT NULL,
  location VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
```

## Maven 
We used maven to build the spring application. Please install maven to build the application. Use this guide https://maven.apache.org/install.html for the installation. 

All the further dependencies are specified in the pom.xml of the project which are downloaded when we run the application. 

Application runs on port 8001. 



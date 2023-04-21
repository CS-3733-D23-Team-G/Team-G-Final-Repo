package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.EmployeeDAO;
import edu.wpi.teamg.ORMClasses.Employee;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EmployeeInformation {
@FXML Text EmployeeName;
Employee employee = new Employee();
public void getEmployeeInformation() {
    EmployeeName.setText(employee.getFirstName() + " " + employee.getLastName());
}
public static void main(String[] args) {
    EmployeeDAO employeeDAO = new EmployeeDAO();
    Employee employee = new Employee();
    System.out.println(employee.getFirstName());
}
    private String name;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String role;

    public EmployeeInformation(
        String name,
        String username,
        String password,
        String email,
        String phoneNumber,
        String role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }
}

package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EmployeeInformation {
  @FXML Text EmployeeName;

  public void initialize() {
    getEmployeeInformation();
  }

  public void getEmployeeInformation() {
    EmployeeName.setText("" + App.employee.getFirstName() + " " + App.employee.getLastName());
  }
}

//  private String name;
//  private String username;
//  private String password;
//  private String email;
//  private String phoneNumber;
//  private String role;
//
//  public EmployeeInformation(
//      String name,
//      String username,
//      String password,
//      String email,
//      String phoneNumber,
//      String role) {
//    this.name = name;
//    this.username = username;
//    this.password = password;
//    this.email = email;
//    this.phoneNumber = phoneNumber;
//    this.role = role;
//  }
//
//  public String getName() {
//    return name;
//  }
//
//  public String getUsername() {
//    return username;
//  }
//
//  public String getPassword() {
//    return password;
//  }
//
//  public String getEmail() {
//    return email;
//  }
//
//  public String getPhoneNumber() {
//    return phoneNumber;
//  }
//
//  public String getRole() {
//    return role;
//  }
// }

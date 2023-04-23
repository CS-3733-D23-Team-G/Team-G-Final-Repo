package edu.wpi.teamg.controllers;
import edu.wpi.teamg.DAOs.EdgeDAO;
import edu.wpi.teamg.ORMClasses.Edge;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AboutPageController {
    @FXML Label About_label;
    public void initialize() {
        String about_text= "WPI Computer Science Department, CS3733-D23 Software Engineering, Prof. Wilson Wong,\n" +
                "Teach Coach: Luke Deratzou, Ian Wright, Micheal O’Connor, Qui Nguyen. \n" +
                "Lead Software Engineer: Thomas McDonagh, Aaron Mar\n" +
                "Assistant Lead Software Engineer:  Nathan Shemesh, Rajesh Ganguli\n" +
                "Scrum Master: Rishi Patel\n" +
                "Documentation Analyst: Sebastian Baldini\n" +
                "Project Manager: Kristine Guan\n" +
                "Product Onwer: Andrew Simonini\n" +
                "Full-time Engineer: Mohamed Barry, Viet Hung Pham\n" +
                "\n" +
                "Thank you to the Brigham and Women’s Hospital and their representative, Andrew Shinn, for their time and input with the project\n ";
      About_label.setText(about_text);
    }


}

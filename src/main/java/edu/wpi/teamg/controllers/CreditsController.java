package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CreditsController {
  @FXML Text Text_about;
  @FXML MFXButton Credits;

  public void initialize() {
    Credits.setOnMouseClicked(event -> Navigation.navigate(Screen.ABOUT_PAGE));
    ;
    String about_text =
        "PHOTOS \n"
            + "Pretzel Photo by Lisa Fotios from Pexels \n"
            + "Goldfish Photo by Em Hopper from Pexels \n"
            + "Avocado Toast Photo by Em Hopper from Pexel \n"
            + "Bacon Photo by Polina Tankilevitch from Pexels \n"
            + "Chips Photo by icon0.com from Pexels \n"
            + "Fries Photo by Dzenina Lukac from Pexels \n"
            + "Smoothie Photo by Element5 Digital from Pexels \n"
            + "Tea Photo by Olga Mironova from Pexels \n"
            + "Orange Juice Photo by Bruno Scramgnon from Pexels \n"
            + "Water Photo by Ray Piedra from Pexels \n"
            + "Soda Photo by Marta Dzedyshko from Pexels \n"
            + "Coffee Photo by Tyler Nix from Pexels \n"
            + "Pizza Photo by ROMAN ODINTSOV from Pexels \n"
            + "Sandwich Photo by Pixabay from Pexels \n"
            + "Taco Photo by Chitokan C. from Pexels \n"
            + "Sushi Photo by Chitokan C. from Pexels \n"
            + "Hot Dog Photo by Mark Neal from Pexels \n"
            + "Burger Photo Photo by Mark Neal from Pexels\n"
            + "API\n"
            + "Mail API https://mvnrepository.com/artifact/javax.mail/mail/1.4.1 \n"
            + "Yaml  https://mvnrepository.com/artifact/org.yaml/snakeyaml/1.8 \n"
            + "Dictionary API https://dictionaryapi.com/";

    Text_about.setText(about_text);
  }
}

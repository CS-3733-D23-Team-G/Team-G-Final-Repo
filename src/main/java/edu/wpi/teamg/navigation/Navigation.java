package edu.wpi.teamg.navigation;

import edu.wpi.teamg.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javax.swing.*;

public class Navigation {
  static Boolean AdminCheck;
  static Boolean EmployeeCheck;

  public static void navigate(final Screen screen) {

    final String filename = screen.getFilename();
    try {
      final var resource = App.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);

      App.getRootPane().setCenter(loader.load());

      if (screen == Screen.SIGNAGE_PAGE) {
        PaitentBanner();
      } else if (screen == Screen.PATHFINDING_PAGE) {
        if (EmployeeCheck) {
          AdminBannerManager();
        } else {
          PaitentBanner();
        }
      } else if (screen == Screen.LOGIN_PAGE) {
        LoginBanner();
      } else {
        AdminBannerManager();
      }
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  public static void AdminBannerManager() {
    if (AdminCheck) {
      final FXMLLoader BannerLoaderAdmin =
          new FXMLLoader(App.class.getResource("views/AdminTopBorder.fxml"));
      final Node AdminBanner = BannerLoaderAdmin.load();
      App.getRootPane().setTop(AdminBanner);
    } else {
      final FXMLLoader BannerLoader1 =
          new FXMLLoader(App.class.getResource("views/TopBorder.fxml"));
      final Node MainBanner = BannerLoader1.load();
      App.getRootPane().setTop(MainBanner);
    }
  }

  public static void PaitentBanner() {
    final FXMLLoader BannerLoaderPatient =
        new FXMLLoader(App.class.getResource("views/PatientBanner.fxml"));
    final Node PatientBanner = BannerLoaderPatient.load();
    App.getRootPane().setTop(PatientBanner);
  }

  public static void LoginBanner() {
    final FXMLLoader BannerLoaderLogin =
        new FXMLLoader(App.class.getResource("views/LoginBanner.fxml"));
    final Node LoginBanner = BannerLoaderLogin.load();
    App.getRootPane().setTop(LoginBanner);
  }

  public static void setAdmin() {
    AdminCheck = true;
  }

  public static void setLoggedin() {
    EmployeeCheck = true;
  }
}

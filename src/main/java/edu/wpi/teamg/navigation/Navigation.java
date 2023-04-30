package edu.wpi.teamg.navigation;

import edu.wpi.teamg.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javax.swing.*;

public class Navigation {
  static Boolean AdminCheck = false;
  static Boolean EmployeeCheck = false;

  public static void navigate(final Screen screen) {

    final String filename = screen.getFilename();
    try {
      final var resource = App.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);

      App.getRootPane().setCenter(loader.load());

      if (screen == Screen.SIGNAGE_SCREENSAVER_PAGE) {
        Logout();
        PaitentBanner();
      } else if (screen == Screen.PATHFINDING_PAGE) {
        if (EmployeeCheck) {
          AdminBannerManager();
        } else {
          PaitentBanner();
        }
      } else if (screen == Screen.LOGIN_PAGE) {
        LoginBanner();
      } else if (screen != Screen.LOGIN_PAGE
          && screen != Screen.PATHFINDING_PAGE
          && screen != Screen.SIGNAGE_SCREENSAVER_PAGE) {
        AdminBannerManager();
      }
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  public static void AdminBannerManager() throws IOException {
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

  public static void PaitentBanner() throws IOException {
    final FXMLLoader BannerLoaderPatient =
        new FXMLLoader(App.class.getResource("views/PatientBanner.fxml"));
    final Node PatientBanner = BannerLoaderPatient.load();
    App.getRootPane().setTop(PatientBanner);
  }

  public static void LoginBanner() throws IOException {
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

  public static void Logout() {
    AdminCheck = false;
    EmployeeCheck = false;
  }
}

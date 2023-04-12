package edu.wpi.teamg.navigation;

import edu.wpi.teamg.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javax.swing.*;

public class Navigation {
  public static void navigate(final Screen screen) {
    //    App.getRootPane().setTop(null);
    final String filename = screen.getFilename();
    try {
      final var resource = App.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);
      if (screen == Screen.SIGNAGE_PAGE) {
        App.getRootPane().setTop(null);
      }
      if (screen == Screen.LOGIN_PAGE) {
        final FXMLLoader LoginLoader =
            new FXMLLoader(App.class.getResource("views/LoginBanner.fxml"));
        final Node Login = LoginLoader.load();
        App.getRootPane().setTop(Login);

      } else {

        final FXMLLoader BannerLoader =
            new FXMLLoader(App.class.getResource("views/TopBorder.fxml"));
        final Node MainBanner = BannerLoader.load();
        App.getRootPane().setTop(MainBanner);
      }
      App.getRootPane().setCenter(loader.load());
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }
}

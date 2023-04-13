package edu.wpi.teamg.navigation;

import edu.wpi.teamg.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javax.swing.*;

public class Navigation {
  public static void navigate(final Screen screen) {

    final String filename = screen.getFilename();
    try {
      final var resource = App.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);

      App.getRootPane().setCenter(loader.load());

      if (screen != Screen.SIGNAGE_PAGE && screen != Screen.LOGIN_PAGE) {
        final FXMLLoader BannerLoader1 =
            new FXMLLoader(App.class.getResource("views/TopBorder.fxml"));
        final Node MainBanner = BannerLoader1.load();
        App.getRootPane().setTop(MainBanner);
      } else if (screen == Screen.LOGIN_PAGE) {
        final FXMLLoader BannerLoader2 =
            new FXMLLoader(App.class.getResource("views/LoginBanner.fxml"));
        final Node LoginBanner = BannerLoader2.load();
        App.getRootPane().setTop(LoginBanner);
      } else {
        App.getRootPane().setTop(null);
      }
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }
}

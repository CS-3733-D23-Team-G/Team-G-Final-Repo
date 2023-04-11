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
      if (screen == Screen.SIGNAGE_PAGE) {
        App.getRootPane().setTop(null);

      } else {

        final FXMLLoader loader2 = new FXMLLoader(App.class.getResource("views/TopBorder.fxml"));
        final Node root = loader2.load();
        App.getRootPane().setTop(root);
      }
      App.getRootPane().setCenter(loader.load());
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }
}

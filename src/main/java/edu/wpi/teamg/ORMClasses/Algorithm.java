package edu.wpi.teamg.ORMClasses;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Algorithm {
  public ArrayList<String> process(
      MFXFilterComboBox startLocDrop, MFXFilterComboBox endLocDrop, ArrayList<Move> movin)
      throws SQLException;
}

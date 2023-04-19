package edu.wpi.teamg.ORMClasses;

import java.sql.SQLException;
import java.util.ArrayList;
import org.controlsfx.control.SearchableComboBox;

public interface Algorithm {
  public ArrayList<String> process(SearchableComboBox startLocDrop, SearchableComboBox endLocDrop)
      throws SQLException;
}

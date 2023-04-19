package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import org.controlsfx.control.SearchableComboBox;

public interface Algorithm {
  public ArrayList<String> process(SearchableComboBox startLocDrop, SearchableComboBox endLocDrop, Date date)
      throws SQLException;
}

package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.controlsfx.control.PopOver;

public class EditorInstructionsPopOverController {

  @FXML TextArea instructions;

  @FXML MFXButton closeHelp;
  PopOver wind;

  public void initialize() {
    closeHelp.setOnMouseClicked(
        event -> {
          closeHelpMenu();
        });

    instructions.setEditable(false);

    instructions.setText(
        "Map Controls:\n"
            + "To zoom in or out, press Ctrl and scroll with the mouse.\n"
            + "To adjust the map, click and drag the map.\n"
            + "To change floors, click on the blue buttons found to the left of the map.\n"
            + "To toggle the nodes or the short names on the map, click the toggle buttons. (Yellow means On, Grey means Off)\n\n"
            + "Map Functionalities:\n"
            + "Above you can see 4 drop down menus that represent Nodes, Edges, Location Name, and Move Edits.\n"
            + "Click the tab of the feature you want to edit to get a dropdown of options associated with the functionality.\n"
            + "To hide the functionality, click the the tab a second time.\n\n"
            + "Node:\n"
            + "To add a Node, click the Add Node button and fill out the prompt\n"
            + "To see, edit, or delete a node, right click the node\n"
            + "To modify, edit the fields and press submit (Note: NodeID and Short Name are not editable). To delete, click the delete button.\n"
            + "To align nodes, click the node align button. From there, select the nodes you would like to align and click the horizontal or vertical button to align the selected nodes\n"
            + "If you just want to move the node itself, drag and drop the node to its new location and confirm the change on the pop up.\n\n"
            + "Edges:\n"
            + "To add an edge, click the add edge button. From there, you will be able to create a new edge by clicking 2 nodes\n"
            + "To delete an edge, just look up the edge ID in the dropdown and click delete\n\n"
            + "Location Names:\n"
            + "To add, modify, or delete location names, click the specified labeled button and look up the long name.\n"
            + "From there you can modify or review the fields depending on the button. Click submit to submit changes\n\n"
            + "Moves:\n"
            + "To add or delete moves, just click on the specified buttons and fill out the fields.\n"
            + "To see future moves in a specific location, Right click a node and select future moves\n");
  }

  public void setW(PopOver window) {
    this.wind = window;
  }

  public void closeHelpMenu() {
    wind.hide();
  }
}

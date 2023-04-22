package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DictionaryController {
  @FXML Text outputText;
  @FXML MFXButton inputButton;

  @FXML MFXTextField input;

  public void initialize() throws SQLException {
    inputButton.setOnMouseClicked(event -> DictionaryLookup());
  }

  public void DictionaryLookup() {
    String searchVal = input.getText();
    String output = "Nah Fam";

    try {

      URL url =
          new URL(
              "https://dictionaryapi.com/api/v3/references/medical/json/"
                  + searchVal
                  + "?key=fe2dc5dc-c361-4bbb-8e45-e6a51a4141aa");

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.connect();

      // Check if connect is made
      int responseCode = conn.getResponseCode();

      // 200 OK
      if (responseCode != 200) {
        throw new RuntimeException("HttpResponseCode: " + responseCode);
      } else {

        StringBuilder informationString = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
          informationString.append(scanner.nextLine());
        }
        // Close the scanner
        scanner.close();

        System.out.println(informationString);

        // JSON simple library Setup with Maven is used to convert strings to JSON
        JSONParser parse = new JSONParser();
        JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));

        // Get the first JSON object in the JSON array
        System.out.println(dataObject.get(0));

        JSONObject countryData = (JSONObject) dataObject.get(0);

        output = countryData.get("shortdef").toString();
        int letters = output.length();
        output = output.substring(1, (output.length() - 1));
        outputText.wrappingWidthProperty().set(200);
        outputText.setText(output);
        conn.disconnect();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;

import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.text.html.ImageView;

public class DictionaryController {
  @FXML Text outputText;
  @FXML Text typeOfWord;
  @FXML Text Word;
  //  @FXML Text synonyms;
  @FXML MFXButton inputButton;

  @FXML MFXTextField input;
//  @FXML  ImageView logoMW;

  public void initialize() throws SQLException {
    inputButton.setOnMouseClicked(event -> SpanishLookup());
//    Image sushi = new Image(App.class.getResourceAsStream("Images/mwlogo.jpg"));


  }



  public void SpanishLookup() {
    String FinalOutput;
    String searchValSpan = input.getText();
    String spanishOutput;

    try {
      URL url =
          new URL(
              "https://dictionaryapi.com/api/v3/references/spanish/json/"
                  + searchValSpan
                  + "?key=263cfa5e-6dec-4781-9798-412cd53d4373  ");

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

        JSONObject JSONOut = (JSONObject) dataObject.get(0);

        spanishOutput = JSONOut.get("shortdef").toString();

        JSONObject metaJSON = (JSONObject) JSONOut.get("meta");
        int letters = spanishOutput.length();
        if (spanishOutput.contains(",")) {
          spanishOutput = spanishOutput.substring(2, spanishOutput.indexOf(","));
        } else spanishOutput = spanishOutput.substring(2, letters - 2);
        System.out.println(spanishOutput);
        boolean langCheck = metaJSON.get("lang").toString().equals("es");
        if (langCheck) {
          DictionaryLookup(spanishOutput);
        } else {
          DictionaryLookup(searchValSpan);
        }
        conn.disconnect();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void DictionaryLookup(String searchInput) {
    String output = "Nah Fam";

    try {

      URL url =
          new URL(
              "https://dictionaryapi.com/api/v3/references/medical/json/"
                  + searchInput
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
        //        System.out.println("doctor");
        //        System.out.println(informationString);

        // JSON simple library Setup with Maven is used to convert strings to JSON
        JSONParser parse = new JSONParser();
        JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));

        // Get the first JSON object in the JSON array
        //        System.out.println(dataObject.get(0));

        JSONObject JSONOut = (JSONObject) dataObject.get(0);

        output = JSONOut.get("shortdef").toString();
        int letters = output.length();
        output = output.substring(1, (output.length() - 1));
        //        synonyms.setText(countryData.get("stems").toString());
        typeOfWord.setText(JSONOut.get("fl").toString());
        Word.setText(searchInput);
        Word.setStyle("-fx-underline: true");
        outputText.wrappingWidthProperty().set(1470);
        outputText.setText(output);
        conn.disconnect();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

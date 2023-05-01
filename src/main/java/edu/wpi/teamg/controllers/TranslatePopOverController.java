package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TranslatePopOverController {

    @FXML TextArea language;
    @FXML
    TextArea userInput;

    @FXML
    TextArea translatedInput;

    @FXML MFXButton translateButton;


    private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
    private static final String CLIENT_SECRET = "PUBLIC_SECRET";
    private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

    public void initialize() {
        userInput.setVisible(true);
        translatedInput.setVisible(true);
        translateButton.setOnMouseClicked(
                event -> {
                    try {
                        translate();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    public void translate() throws Exception{
        String fromLang = "en";
        String toLang;
        String text = String.valueOf(userInput);


        toLang = switch (String.valueOf(language)) {
            case "English" -> "en";
            case "Spanish" -> "es";
            case "French" -> "fr";
            case "German" -> "de";
            case "Russian" -> "ru";
            default -> "Language Unknown";
        };

        if(!(toLang.equals("Language Unknown"))) {
            //TranslatePopOverController.translate(fromLang, toLang, text);

            // TODO: Should have used a 3rd party library to make a JSON string from an object
            String jsonPayload = new StringBuilder()
                    .append("{")
                    .append("\"fromLang\":\"")
                    .append(fromLang)
                    .append("\",")
                    .append("\"toLang\":\"")
                    .append(toLang)
                    .append("\",")
                    .append("\"text\":\"")
                    .append(text)
                    .append("\"")
                    .append("}")
                    .toString();

            URL url = new URL(ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
            conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(jsonPayload.getBytes());
            os.flush();
            os.close();

            int statusCode = conn.getResponseCode();
            //System.out.println("Status Code: " + statusCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
            ));

            String.valueOf(translatedInput).equals(br.readLine());
            conn.disconnect();
        }
    }
}

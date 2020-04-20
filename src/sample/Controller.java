package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Text temp_feels;

    @FXML
    private Text pressure;

    @FXML
    void initialize() {
        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            if (!getUserCity.equals("")) {

                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=5d1ca6a73444f8a9edf8400a02be9ab3");

                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);

                    double temperature = obj.getJSONObject("main").getDouble("temp") - 273.00;
                    temperature = Double.parseDouble(String.format("%.2f\n", temperature).replace(",", "."));
                    temp_info.setText("Temperature: " + temperature + "℃");

                    double felt = obj.getJSONObject("main").getDouble("feels_like") - 273.00;
                    felt = Double.parseDouble(String.format("%.2f\n", felt).replace(",", "."));
                    temp_feels.setText("Felt: " + felt + "℃");

                    double max = obj.getJSONObject("main").getDouble("temp_max") - 273.00;
                    max = Double.parseDouble(String.format("%.2f\n", max).replace(",", "."));
                    temp_max.setText("Max: " + max + "℃");

                    double min = obj.getJSONObject("main").getDouble("temp_min") - 273.00;
                    min = Double.parseDouble(String.format("%.2f\n", min).replace(",", "."));
                    temp_min.setText("Min: " + min + "℃");

                    double pres_o = obj.getJSONObject("main").getDouble("pressure");
                    pres_o = Double.parseDouble(String.format("%.2f\n", pres_o).replace(",", "."));
                    pressure.setText("Pressure: " + pres_o + " Pa");
                }
            } else warningMessage();
        });
    }


    public String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            warningMessage();
        }
        return content.toString();
    }

    public void warningMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Input correct field city");
        alert.showAndWait();
    }

}

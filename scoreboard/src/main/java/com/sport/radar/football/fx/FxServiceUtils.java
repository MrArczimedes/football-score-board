package com.sport.radar.football.fx;

import javafx.scene.control.Alert;

public class FxServiceUtils {

    public static void showFxErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error info");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}

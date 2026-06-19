package com.sport.radar.football;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/sport/radar/football/fx/controllers/view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("World cup football championship scoreboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
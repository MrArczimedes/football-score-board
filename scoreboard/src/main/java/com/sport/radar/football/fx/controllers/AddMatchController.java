package com.sport.radar.football.fx.controllers;

import com.sport.radar.football.exceptions.TeamNameException;
import com.sport.radar.football.fx.FxServiceUtils;
import com.sport.radar.football.service.ScoreBoardService;
import com.sport.radar.football.service.impl.ScoreBoardServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMatchController {

    private final ScoreBoardService scoreBoardService = ScoreBoardServiceImpl.getInstance();

    @FXML
    private TextField homeTeamField;
    @FXML
    private TextField awayTeamField;

    @FXML
    private void saveMatch() {
        try {
            scoreBoardService.addMatch(homeTeamField.getText(), awayTeamField.getText());
        } catch (TeamNameException e) {
            FxServiceUtils.showFxErrorAlert(e.getMessage());
        }

        Stage stage = (Stage) homeTeamField.getScene().getWindow();
        stage.close();
    }
}

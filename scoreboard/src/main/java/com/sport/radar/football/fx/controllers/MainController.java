package com.sport.radar.football.fx.controllers;

import com.sport.radar.football.exceptions.CannotUndoException;
import com.sport.radar.football.fx.FxServiceUtils;
import com.sport.radar.football.model.Match;
import com.sport.radar.football.service.ScoreBoardService;
import com.sport.radar.football.service.impl.ScoreBoardServiceImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private TableView<Match> scoreboard;
    @FXML
    private TableColumn<Match, Void> leftGoalButtons;
    @FXML
    private TableColumn<Match, Void> rightGoalButtons;
    @FXML
    private TableColumn<Match, Void> finishMatchAndWalkOverButtons;
    @FXML
    private TableColumn<Match, String> homeTeamName;
    @FXML
    private TableColumn<Match, String> awayTeamName;
    @FXML
    private TableColumn<Match, Integer> homeTeamScore;
    @FXML
    private TableColumn<Match, Integer> awayTeamScore;
    @FXML
    private TableView<Match> summaryBoard;
    @FXML
    private TableColumn<Match, String> summaryHomeTeamName;
    @FXML
    private TableColumn<Match, String> summaryAwayTeamName;
    @FXML
    private TableColumn<Match, Integer> summaryHomeTeamScore;
    @FXML
    private TableColumn<Match, Integer> summaryAwayTeamScore;

    @FXML
    private ObservableList<Match> matchList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Match> summaryList = FXCollections.observableArrayList();

    private final ScoreBoardService scoreBoardService = ScoreBoardServiceImpl.getInstance();


    @FXML
    public void initialize() {
        scoreboard.setItems(matchList);

        homeTeamName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHomeTeamName()));
        awayTeamName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAwayTeamName()));
        homeTeamScore.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getHomeTeamScore()).asObject());
        awayTeamScore.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAwayTeamScore()).asObject());

        summaryBoard.setItems(summaryList);

        summaryHomeTeamName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHomeTeamName()));
        summaryAwayTeamName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAwayTeamName()));
        summaryHomeTeamScore.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getHomeTeamScore()).asObject());
        summaryAwayTeamScore.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAwayTeamScore()).asObject());


        leftGoalButtons.setCellFactory(col -> new TableCell<>() {
            private final Button homeGoal = new Button("HOME_SCORE");
            private final Button homeUnscore = new Button("HOME_UNSCORE");
            private final VBox container = new VBox(5, homeGoal, homeUnscore); //

            {
                container.setAlignment(Pos.CENTER);
                homeGoal.setOnAction(e -> {
                    if (!isEmpty()) {
                        Match match = getTableView().getItems().get(getIndex());
                        scoreBoardService.homeTeamScores(match.getId());
                        refreshTable();
                    }
                });

                homeUnscore.setOnAction(e -> {
                    if (!isEmpty()) {
                        Match match = getTableView().getItems().get(getIndex());
                        try {
                            scoreBoardService.undoHomeTeamGoal(match.getId());
                            refreshTable();
                        } catch (CannotUndoException ex) {
                            FxServiceUtils.showFxErrorAlert(ex.getMessage());
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });


        rightGoalButtons.setCellFactory(col -> new TableCell<>() {
            private final Button awayGoal = new Button("AWAY_SCORE");
            private final Button awayUnscore = new Button("AWAY_UNSCORE");
            private final VBox container = new VBox(5, awayGoal, awayUnscore);

            {
                container.setAlignment(Pos.CENTER);
                awayGoal.setOnAction(e -> {
                    if (!isEmpty()) {
                        Match match = getTableView().getItems().get(getIndex());
                        scoreBoardService.awayTeamScores(match.getId());
                        refreshTable();
                    }
                });

                awayUnscore.setOnAction(e -> {
                    if (!isEmpty()) {
                        Match match = getTableView().getItems().get(getIndex());
                        try {
                            scoreBoardService.undoAwayTeamGoal(match.getId());
                            refreshTable();
                        } catch (CannotUndoException ex) {
                            FxServiceUtils.showFxErrorAlert(ex.getMessage());
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });

        finishMatchAndWalkOverButtons.setCellFactory(col -> new TableCell<>() {
            private final Button finishButton = new Button("FINISH");
            private final Button walkOverButton = new Button("WALKOVER");
            private final VBox container = new VBox(5, finishButton, walkOverButton);

            {
                container.setAlignment(Pos.CENTER);

                finishButton.setOnAction(e -> {
                    if (!isEmpty()) {
                        Match match = getTableView().getItems().get(getIndex());
                        scoreBoardService.finishMatch(match.getId());
                        refreshTable();
                    }
                });

                walkOverButton.setOnAction(e -> {
                    if (!isEmpty()) {
                        Match match = getTableView().getItems().get(getIndex());
                        showWalkOverDialog(match);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });

        refreshTable();
    }


    public void refreshTable() {
        Map<Integer, Match> matches = scoreBoardService.getScoreboard();
        matchList.setAll(
                matches.values()
                        .stream()
                        .filter(match -> !match.isFinished())
                        .sorted(Comparator.comparing(Match::getId))
                        .toList());
        refreshSummary(matches);
    }

    public void refreshSummary(Map<Integer, Match> matches) {
        summaryList.setAll(
                matches.values()
                        .stream()
                        .sorted(Comparator.comparingInt((Match match) -> match.getAwayTeamScore() + match.getHomeTeamScore()).reversed())
                        .toList());
    }

    private void showWalkOverDialog(Match match) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Walk-over Window");
        alert.setHeaderText("Match: " + match.getHomeTeamName() + " vs " + match.getAwayTeamName());
        alert.setContentText("Who winning by walk-over?");

        ButtonType homeWinner = new ButtonType("Wins: " + match.getHomeTeamName());
        ButtonType awayWinner = new ButtonType("Wins: " + match.getAwayTeamName());
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(homeWinner, awayWinner, cancelBtn);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == homeWinner) {
                scoreBoardService.walkoverForHomeTeam(match.getId());
                refreshTable();
            } else if (result.get() == awayWinner) {
                scoreBoardService.walkoverForAwayTeam(match.getId());
                refreshTable();
            }
        }
    }


    @FXML
    private void openNewMatchView() {
        try {
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/com/sport/radar/football/fx/controllers/addMatchView.fxml"));
            Parent root = loader.load();

            Stage addMatchView = new Stage();
            addMatchView.setTitle("Add match");
            addMatchView.setScene(new Scene(root));

            addMatchView.initModality(Modality.APPLICATION_MODAL);

            addMatchView.showAndWait();
            refreshTable();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error during opening window!");
        }
    }
}



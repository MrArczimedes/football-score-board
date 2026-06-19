package com.sport.radar.football.service.impl;

import com.sport.radar.football.exceptions.TeamNameException;
import com.sport.radar.football.model.Match;
import com.sport.radar.football.service.ScoreBoardService;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreBoardServiceImpl implements ScoreBoardService {

    private final Map<Integer, Match> scoreBoard = new ConcurrentHashMap<>();
    private int matchSequenceId = 1;

    //USED FOR TESTS
    public ScoreBoardServiceImpl(Map<Integer, Match> testData) {
        scoreBoard.clear();
        scoreBoard.putAll(testData);
        matchSequenceId = testData.size()+1;
    }

    public ScoreBoardServiceImpl() {
    }

    @Override
    public Map<Integer, Match> getScoreboard() {
        return scoreBoard;
    }

    @Override
    public void addMatch(String homeTeamName, String awayTeamName) {
        addMatchPreconditions(homeTeamName, awayTeamName);
        scoreBoard.put(matchSequenceId, new Match(matchSequenceId, homeTeamName, awayTeamName));
        increaseSequence();
    }

    @Override
    public void finishMatch(int id) {
        scoreBoard.get(id).finishMatch();
    }

    @Override
    public void homeTeamScores(int id) {
        scoreBoard.get(id).homeTeamScores();
    }

    @Override
    public void awayTeamScores(int id) {
        scoreBoard.get(id).awayTeamScores();

    }

    @Override
    public void undoHomeTeamGoal(int id) {
        scoreBoard.get(id).undoHomeTeamGoal();
    }

    @Override
    public void undoAwayTeamGoal(int id) {
        scoreBoard.get(id).undoAwayTeamGoal();
    }

    @Override
    public void walkoverForHomeTeam(int id) {
        scoreBoard.get(id).walkOverForHomeTeam();
    }

    @Override
    public void walkoverForAwayTeam(int id) {
        scoreBoard.get(id).walkOverForAwayTeam();
    }

    private void increaseSequence() {
        matchSequenceId++;
    }

    private void addMatchPreconditions(String homeTeamName, String awayTeamName) {
        Optional<String> homeTeamError = Optional.ofNullable(namePreconditions(homeTeamName));
        Optional<String> awayTeamError = Optional.ofNullable(namePreconditions(awayTeamName));
        if (homeTeamError.isPresent() || awayTeamError.isPresent()) {
            throw new TeamNameException(
                    homeTeamError.map(s -> "Home team name " + s).orElse("") +
                            awayTeamError.map(s -> " Away team name " + s).orElse(""));
        }
    }

    private String namePreconditions(String teamName) {
        if (Objects.isNull(teamName)) {
            return "is null";
        }
        if (teamName.isBlank()) {
            return "is empty";
        }

        String trimmedName = teamName.trim();
        if (!trimmedName.matches("^[\\p{L}\\s-]+$")) {
            return "contains invalid characters";
        }

        return null;
    }
}

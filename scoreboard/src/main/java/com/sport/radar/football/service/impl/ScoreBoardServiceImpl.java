package com.sport.radar.football.service.impl;

import com.sport.radar.football.model.Match;
import com.sport.radar.football.service.ScoreBoardService;

import java.util.Map;
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

    }

    @Override
    public void finishMatch(int id) {

    }

    @Override
    public void homeTeamScores(int id) {

    }

    @Override
    public void awayTeamScores(int id) {

    }

    @Override
    public void undoHomeTeamGoal(int id) {

    }

    @Override
    public void undoAwayTeamGoal(int id) {

    }
}

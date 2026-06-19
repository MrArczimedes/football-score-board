package com.sport.radar.football.service.impl;

import com.sport.radar.football.model.Match;
import com.sport.radar.football.service.ScoreBoardService;

import java.util.Map;

public class ScoreBoardServiceImpl implements ScoreBoardService {
    @Override
    public Map<Integer, Match> getScoreboard() {
        return Map.of();
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

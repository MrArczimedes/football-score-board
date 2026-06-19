package com.sport.radar.football.service;

import com.sport.radar.football.model.Match;

import java.util.Map;

public interface ScoreBoardService {
    Map<Integer, Match> getScoreboard();
    void addMatch(String homeTeamName, String awayTeamName);
    void finishMatch(int id);
    void homeTeamScores(int id);
    void awayTeamScores(int id);
    void undoHomeTeamGoal(int id);
    void undoAwayTeamGoal(int id);
}

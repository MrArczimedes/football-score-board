package com.sport.radar.football.model;

public class TeamWithScore {

    private final String teamName;
    private int score = 0;

    public TeamWithScore(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getScore() {
        return score;
    }

    public void addGoal() {
        this.score++;
    }
}

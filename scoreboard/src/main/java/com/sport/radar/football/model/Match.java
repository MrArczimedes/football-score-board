package com.sport.radar.football.model;

public class Match {
    private int id;
    private TeamWithScore homeTeam;
    private TeamWithScore awayTeam;
    private boolean finished = false;

    public Match(int id, String homeTeamName, String awayTeamName) {
        this.id = id;
        this.homeTeam = new TeamWithScore(homeTeamName);
        this.awayTeam = new TeamWithScore(awayTeamName);
    }
}

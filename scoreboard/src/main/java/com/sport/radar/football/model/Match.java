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

    public int getId() {
        return id;
    }

    public TeamWithScore getHomeTeam() {
        return homeTeam;
    }

    public TeamWithScore getAwayTeam() {
        return awayTeam;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getHomeTeamName() {
        return getHomeTeam().getTeamName();
    }

    public String getAwayTeamName() {
        return getAwayTeam().getTeamName();
    }

    public int getHomeTeamScore() {
        return getHomeTeam().getScore();
    }

    public int getAwayTeamScore() {
        return getAwayTeam().getScore();
    }

    public void finishMatch() {
        this.finished = true;
    }

    public void homeTeamScores() {
        homeTeam.addGoal();
    }

    public void awayTeamScores() {
        awayTeam.addGoal();
    }

    public void undoHomeTeamGoal() {
        homeTeam.undoGoal();
    }

    public void undoAwayTeamGoal() {
        awayTeam.undoGoal();
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", finished=" + finished +
                '}';
    }
}

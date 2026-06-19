package com.sport.radar.football.model;

import com.sport.radar.football.exceptions.CannotUndoException;

public class TeamWithScore {

    private final String teamName;
    private int score = 0;
    private boolean undoDone = true;

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
        undoDone = false;
    }

    public void undoGoal() {
        if (!undoDone) {
            this.score--;
            undoDone = true;
        } else if (getScore() <= 0) {
            throw new CannotUndoException("Score cannot be negative");

        } else {
            throw new CannotUndoException("Undo goal is already done");
        }
    }

    void winWalkover() {
        this.score = 3;
    }

    void looseWalkover(){
        this.score = 0;
    }

    @Override
    public String toString() {
        return "TeamWithScore{" +
                "teamName='" + teamName + '\'' +
                ", score=" + score +
                ", undoDone=" + undoDone +
                '}';
    }
}

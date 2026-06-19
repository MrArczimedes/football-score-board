package com.sport.radar.football.service;

import com.sport.radar.football.model.Match;
import com.sport.radar.football.service.impl.ScoreBoardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardServiceTest {
    ScoreBoardService scoreBoardService;

    @Test
    void getScoreboard_expectSameSize_sameData() {
        //GIVEN
        scoreBoardService = new ScoreBoardServiceImpl(testData());
        //WHEN
        Map<Integer, Match> scoreBoard = scoreBoardService.getScoreboard();
        //THEN
        assertEquals(scoreBoard.size(), testData().size());
        for(Map.Entry<Integer, Match> entry : scoreBoard.entrySet()) {
            Match match = testData().get(entry.getKey());
            assertEquals(match.getHomeTeamName(), entry.getValue().getHomeTeamName());
            assertEquals(match.getAwayTeamScore(), entry.getValue().getAwayTeamScore());
            assertEquals(match.getAwayTeamName(), entry.getValue().getAwayTeamName());
            assertEquals(match.getHomeTeamScore(), entry.getValue().getHomeTeamScore());
        }
    }

    @Test
    void addMatch() {
    }

    @Test
    void finishMatch() {
    }

    @Test
    void homeTeamScores() {
    }

    @Test
    void awayTeamScores() {
    }

    @Test
    void undoHomeTeamGoal() {
    }

    @Test
    void undoAwayTeamGoal() {
    }

    private Map<Integer, Match> testData() {
        return Map.of(1, new Match(1, "Brazil", "Poland"),
                2, new Match(2, "Germany", "Uruguay"),
                3, new Match(3, "test31", "test32")
        );
    }
}
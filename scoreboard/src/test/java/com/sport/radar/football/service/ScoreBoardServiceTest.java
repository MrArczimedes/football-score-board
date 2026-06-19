package com.sport.radar.football.service;

import com.sport.radar.football.exceptions.CannotUndoException;
import com.sport.radar.football.exceptions.TeamNameException;
import com.sport.radar.football.model.Match;
import com.sport.radar.football.service.impl.ScoreBoardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardServiceTest {
    ScoreBoardService scoreBoardService = new ScoreBoardServiceImpl();

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
    void addMatch_withSuccess() {
        //WHEN
        scoreBoardService.addMatch("Aaaaa", "Bbbb");
        //THEN
        Map<Integer, Match> scoreboard = scoreBoardService.getScoreboard();
        assertEquals(1, scoreboard.size());
        assertEquals("Aaaaa", scoreboard.get(1).getHomeTeamName());
        assertEquals("Bbbb", scoreboard.get(1).getAwayTeamName());
        assertEquals(0, scoreboard.get(1).getHomeTeamScore());
        assertEquals(0, scoreboard.get(1).getAwayTeamScore());
    }

    @Test
    void addMatch_nameCannotBeNull() {
        //WHEN
        TeamNameException ex = assertThrows(TeamNameException.class, () -> scoreBoardService.addMatch(null, null));
        //THEN
        assertEquals("Home team name is null Away team name is null",  ex.getMessage());
        //WHEN
        TeamNameException ex2 = assertThrows(TeamNameException.class, () -> scoreBoardService.addMatch(null, "Normal"));
        //THEN
        assertEquals("Home team name is null",  ex2.getMessage());
        //WHEN
        TeamNameException ex3 = assertThrows(TeamNameException.class, () -> scoreBoardService.addMatch("Normal", null));
        //THEN
        assertEquals("Away team name is null",  ex3.getMessage());
    }

    @Test
    void addMatch_nameCannotBeEmpty() {
        //WHEN
        TeamNameException ex = assertThrows(TeamNameException.class, () -> scoreBoardService.addMatch("  ", ""));
        //THEN
        assertEquals("Home team name is empty Away team name is empty",  ex.getMessage());
        //WHEN
        TeamNameException ex2 = assertThrows(TeamNameException.class, () -> scoreBoardService.addMatch(" ", "Normal"));
        //THEN
        assertEquals("Home team name is empty",  ex2.getMessage());
        //WHEN
        TeamNameException ex3 = assertThrows(TeamNameException.class, () -> scoreBoardService.addMatch("Normal", " "));
        //THEN
        assertEquals("Away team name is empty",  ex3.getMessage());
    }

    @Test
    void addMatch_nameCannotHaveNumbers_or_specialChars() {
        //WHEN
        TeamNameException ex = assertThrows(TeamNameException.class, () -> scoreBoardService.addMatch("1234", "1234"));
        //THEN
        assertEquals("Home team name contains invalid characters Away team name contains invalid characters",  ex.getMessage());
        //WHEN
        TeamNameException ex2 = assertThrows(TeamNameException.class, () -> scoreBoardService.addMatch("@#$", "Normal"));
        //THEN
        assertEquals("Home team name contains invalid characters",  ex2.getMessage());
        //WHEN
        TeamNameException ex3 = assertThrows(TeamNameException.class, () -> scoreBoardService.addMatch("Normal", "TEST!"));
        //THEN
        assertEquals("Away team name contains invalid characters",  ex3.getMessage());
    }

    @Test
    void finishMatch() {
        //GIVEN
        scoreBoardService = new ScoreBoardServiceImpl(testData());
        //WHEN
        scoreBoardService.finishMatch(1);
        Match match = scoreBoardService.getScoreboard().get(1);
        //THEN
        assertTrue(match.isFinished());
    }

    @Test
    void homeTeamScores_4times() {
        //GIVEN
        scoreBoardService = new ScoreBoardServiceImpl(testData());
        //WHEN
        scoreBoardService.homeTeamScores(2);
        //THEN
        Match match = scoreBoardService.getScoreboard().get(2);
        assertEquals(1, match.getHomeTeamScore());
        //WHEN
        scoreBoardService.homeTeamScores(2);
        //THEN
        match = scoreBoardService.getScoreboard().get(2);
        assertEquals(2, match.getHomeTeamScore());
        //WHEN
        scoreBoardService.homeTeamScores(2);
        //THEN
        match = scoreBoardService.getScoreboard().get(2);
        assertEquals(3, match.getHomeTeamScore());
        //WHEN
        scoreBoardService.homeTeamScores(2);
        //THEN
        match = scoreBoardService.getScoreboard().get(2);
        assertEquals(4, match.getHomeTeamScore());
    }

    @Test
    void awayTeamScores_4times() {
        //GIVEN
        scoreBoardService = new ScoreBoardServiceImpl(testData());
        //WHEN
        scoreBoardService.awayTeamScores(2);
        //THEN
        Match match = scoreBoardService.getScoreboard().get(2);
        assertEquals(1, match.getAwayTeamScore());
        //WHEN
        scoreBoardService.awayTeamScores(2);
        //THEN
        match = scoreBoardService.getScoreboard().get(2);
        assertEquals(2, match.getAwayTeamScore());
        //WHEN
        scoreBoardService.awayTeamScores(2);
        //THEN
        match = scoreBoardService.getScoreboard().get(2);
        assertEquals(3, match.getAwayTeamScore());
        //WHEN
        scoreBoardService.awayTeamScores(2);
        //THEN
        match = scoreBoardService.getScoreboard().get(2);
        assertEquals(4, match.getAwayTeamScore());
    }

    @Test
    void undoHomeTeamGoal() {
        //GIVEN
        scoreBoardService = new ScoreBoardServiceImpl(testData());
        //HOME TEAM SCORE IS 3
        scoreBoardService.homeTeamScores(3);
        scoreBoardService.homeTeamScores(3);
        scoreBoardService.homeTeamScores(3);
        assertEquals(3, scoreBoardService.getScoreboard().get(3).getHomeTeamScore());
        //UNDO GOAL
        scoreBoardService.undoHomeTeamGoal(3);
        assertEquals(2, scoreBoardService.getScoreboard().get(3).getHomeTeamScore());

    }

    @Test
    void undoAwayTeamGoal() {
        //GIVEN
        scoreBoardService = new ScoreBoardServiceImpl(testData());
        //AWAY TEAM SCORE IS 3
        scoreBoardService.awayTeamScores(3);
        scoreBoardService.awayTeamScores(3);
        scoreBoardService.awayTeamScores(3);
        assertEquals(3, scoreBoardService.getScoreboard().get(3).getAwayTeamScore());
        //UNDO GOAL
        scoreBoardService.undoAwayTeamGoal(3);
        assertEquals(2, scoreBoardService.getScoreboard().get(3).getAwayTeamScore());
    }

    @Test
    void cannotUndoHomeTeamGoal_when_resultWillBeNegative(){
        //GIVEN
        scoreBoardService = new ScoreBoardServiceImpl(testData());
        //WHEN
        CannotUndoException ex = assertThrows(CannotUndoException.class, () -> scoreBoardService.undoHomeTeamGoal(3));
        //THEN
        assertEquals("Score cannot be negative", ex.getMessage());
    }

    @Test
    void cannotUndoHomeTeamGoal_when_undoWasAlreadyDone(){
        //GIVEN
        scoreBoardService = new ScoreBoardServiceImpl(testData());
        scoreBoardService.homeTeamScores(3);
        scoreBoardService.homeTeamScores(3);
        scoreBoardService.undoHomeTeamGoal(3);
        //WHEN
        CannotUndoException ex = assertThrows(CannotUndoException.class, () -> scoreBoardService.undoHomeTeamGoal(3));
        //THEN
        assertEquals("Undo goal is already done", ex.getMessage());
    }

    @Test
    void walkoverForHomeTeam() {
        //GIVEN
        scoreBoardService = new ScoreBoardServiceImpl(testData());
        //WHEN
        Match match = scoreBoardService.getScoreboard().get(2);
        match.walkOverForAwayTeam();
        //THEN
        assertEquals(0, match.getHomeTeamScore());
        assertEquals(3, match.getAwayTeamScore());
        assertTrue(match.isFinished());
    }

    @Test
    void walkoverForAwayTeam() {
        //GIVEN
        scoreBoardService = new ScoreBoardServiceImpl(testData());
        //WHEN
        Match match = scoreBoardService.getScoreboard().get(2);
        match.walkOverForHomeTeam();
        //THEN
        assertEquals(0, match.getAwayTeamScore());
        assertEquals(3, match.getHomeTeamScore());
        assertTrue(match.isFinished());
    }
    private Map<Integer, Match> testData() {
        return Map.of(1, new Match(1, "Brazil", "Poland"),
                2, new Match(2, "Germany", "Uruguay"),
                3, new Match(3, "test31", "test32")
        );
    }
}
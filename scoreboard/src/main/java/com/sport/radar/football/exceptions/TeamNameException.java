package com.sport.radar.football.exceptions;

public class TeamNameException extends RuntimeException {
    public TeamNameException(String message) {
        super(message.trim());
    }
}

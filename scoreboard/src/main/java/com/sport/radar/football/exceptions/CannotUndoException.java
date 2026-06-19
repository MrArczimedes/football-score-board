package com.sport.radar.football.exceptions;

public class CannotUndoException extends RuntimeException {
    public CannotUndoException(String message) {
        super(message.trim());
    }
}

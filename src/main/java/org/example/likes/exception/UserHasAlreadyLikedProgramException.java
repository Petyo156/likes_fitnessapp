package org.example.likes.exception;

public class UserHasAlreadyLikedProgramException extends RuntimeException {
    public UserHasAlreadyLikedProgramException(String message) {
        super(message);
    }
}

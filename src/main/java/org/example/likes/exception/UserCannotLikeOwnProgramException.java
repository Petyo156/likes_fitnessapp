package org.example.likes.exception;

public class UserCannotLikeOwnProgramException extends RuntimeException {
    public UserCannotLikeOwnProgramException(String message) {
        super(message);
    }
}

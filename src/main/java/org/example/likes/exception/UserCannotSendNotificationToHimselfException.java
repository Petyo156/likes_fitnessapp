package org.example.likes.exception;

public class UserCannotSendNotificationToHimselfException extends RuntimeException {
    public UserCannotSendNotificationToHimselfException(String message) {
        super(message);
    }
}

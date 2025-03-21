package org.example.likes.web;

import org.example.likes.exception.ExceptionMessages;
import org.example.likes.exception.UserCannotLikeOwnProgramException;
import org.example.likes.exception.UserCannotSendNotificationToHimselfException;
import org.example.likes.exception.UserHasAlreadyLikedProgramException;
import org.example.likes.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionAdvice {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserCannotLikeOwnProgramException.class)
    public ResponseEntity<ErrorResponse> handleUserCannotLikeOwnProgram() {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ExceptionMessages.USER_CANNOT_LIKE_OWN_PROGRAM);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserCannotSendNotificationToHimselfException.class)
    public ResponseEntity<ErrorResponse> handleUserCannotSendNotificationToHimself() {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ExceptionMessages.USER_CANNOT_SEND_NOTIFICATION_TO_HIMSELF);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserHasAlreadyLikedProgramException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyLikedProgram() {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ExceptionMessages.USER_HAS_ALREADY_LIKED_PROGRAM);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundEndpoint() {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),ExceptionMessages.NOT_FOUND_ENDPOINT);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions() {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ExceptionMessages.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

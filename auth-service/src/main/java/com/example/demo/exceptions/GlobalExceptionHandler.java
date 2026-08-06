package com.example.demo.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UsernameNotFoundException.class)
	public Object handleUserNotFound(UsernameNotFoundException exception, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getLocalizedMessage(), request.getDescription(false));
		return errorMessage;
	}

	@ExceptionHandler(InvalidTokenException.class)
	public Object handleInvalidToken(InvalidTokenException exception, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(),
				HttpStatus.FORBIDDEN.getReasonPhrase(), exception.getLocalizedMessage(), request.getDescription(false));
		return errorMessage;
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public Object handleInvalidPassword(InvalidPasswordException exception, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(),
				HttpStatus.UNAUTHORIZED.getReasonPhrase(), exception.getLocalizedMessage(), request.getDescription(false));
		return errorMessage;
	}
	@ExceptionHandler(RuntimeException.class)
	public Object handleInvalidPassword(RuntimeException exception, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(),
				HttpStatus.UNAUTHORIZED.getReasonPhrase(), exception.getLocalizedMessage(), request.getDescription(false));
		return errorMessage;
	}
}

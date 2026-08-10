package com.example.demo.exception;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PurchaseRequestNotFoundException.class)
	public ResponseEntity<?> handleUserNotFound(PurchaseRequestNotFoundException ex, WebRequest request) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.localDateTime(LocalDateTime.now())
				.message(ex.getMessage())
				.path(request.getDescription(false))
				.statusCode(HttpURLConnection.HTTP_NOT_FOUND)
				.build();
		return ResponseEntity.ok(errorMessage);
	}

	@ExceptionHandler(RequesterNotFoundException.class)
	public ResponseEntity<?> handleUserAlreadyExist(RequesterNotFoundException ex, WebRequest request) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.localDateTime(LocalDateTime.now())
				.message(ex.getMessage())
				.path(request.getDescription(false))
				.statusCode(HttpURLConnection.HTTP_NOT_FOUND)
				.build();
		return ResponseEntity.ok(errorMessage);
	}

	
}

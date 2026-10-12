package com.vinod.b2b.exceptions;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(SupplierAlreadyExistException.class)
	public ResponseEntity<?> handleSupplierAlreadyExist(SupplierAlreadyExistException ex, WebRequest request) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.localDateTime(LocalDateTime.now())
				.statusCode(HttpURLConnection.HTTP_MULT_CHOICE)
				.message(ex.getMessage())
				.path(request.getDescription(false))
				.build();
		return ResponseEntity.ok(errorMessage);
	}

	@ExceptionHandler(SupplierNotFoundException.class)
	public ResponseEntity<?> handleSupplierNotFound(SupplierNotFoundException ex, WebRequest request) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.localDateTime(LocalDateTime.now())
				.statusCode(HttpURLConnection.HTTP_MULT_CHOICE)
				.message(ex.getMessage())
				.path(request.getDescription(false))
				.build();
		return ResponseEntity.ok(errorMessage);
	}
}

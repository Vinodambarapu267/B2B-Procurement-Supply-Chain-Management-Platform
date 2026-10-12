package com.vinod.b2b.exceptions;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ApproverNotFoundException.class)
	public ResponseEntity<?> handleApporverNotFound(ApproverNotFoundException ex, WebRequest request) {
		ErrrorMessage errorMessge = ErrrorMessage.builder()
				.localDateTime(LocalDateTime.now())
				.message(ex.getMessage())
				.statusCode(HttpURLConnection.HTTP_NOT_FOUND)
				.path(request.getDescription(false))
				.build();
		return ResponseEntity.ok(errorMessge);
	}
	@ExceptionHandler(PurchaseRequestNotFoundException.class)
	public ResponseEntity<?> handlePurchaseRequestNotFound(PurchaseRequestNotFoundException ex, WebRequest request) {
		ErrrorMessage errorMessge = ErrrorMessage.builder()
				.localDateTime(LocalDateTime.now())
				.message(ex.getMessage())
				.statusCode(HttpURLConnection.HTTP_NOT_FOUND)
				.path(request.getDescription(false))
				.build();
		return ResponseEntity.ok(errorMessge);
	}
}

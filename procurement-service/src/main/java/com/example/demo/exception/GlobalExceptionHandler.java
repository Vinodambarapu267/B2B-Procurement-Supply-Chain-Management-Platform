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
	public ResponseEntity<?> handlePurchaseRequestNotFound(PurchaseRequestNotFoundException ex, WebRequest request) {
		ErrorMessage errorMessage = ErrorMessage.builder()
		        .localDateTime(LocalDateTime.now())
		        .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
		        .message(ex.getMessage())
		        .path(request.getDescription(false)).build();
		return ResponseEntity.ok(errorMessage);
	}
	@ExceptionHandler
	public ResponseEntity<?> handleRequesterNotFound(RequesterNotFoundException ex, WebRequest request){
		ErrorMessage errorMessage = ErrorMessage.builder()
		        .localDateTime(LocalDateTime.now())
		        .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
		        .message(ex.getMessage())
		        .path(request.getDescription(false)).build();
		return ResponseEntity.ok(errorMessage);
	}

}

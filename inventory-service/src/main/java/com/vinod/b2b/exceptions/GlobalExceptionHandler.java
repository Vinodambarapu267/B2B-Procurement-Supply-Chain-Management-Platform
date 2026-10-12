package com.vinod.b2b.exceptions;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InventoryItemAlreadyExistException.class)
	public ResponseEntity<?> handleSkuAlreadyExist(InventoryItemAlreadyExistException exception, WebRequest request) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.localDateTime(LocalDateTime.now())
				.message(exception.getLocalizedMessage())
				.statusCode(HttpURLConnection.HTTP_NOT_ACCEPTABLE)
				.path(request.getDescription(false))
				.build();
		return ResponseEntity.ok(errorMessage);
	}

	@ExceptionHandler(InventoryItemNotFoundException.class)
	public ResponseEntity<?> handleItemNotFound(InventoryItemNotFoundException exception, WebRequest request) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.localDateTime(LocalDateTime.now())
				.message(exception.getLocalizedMessage())
				.statusCode(HttpURLConnection.HTTP_NOT_FOUND)
				.path(request.getDescription(false)).build();
		return ResponseEntity.ok(errorMessage);
	}
	
	@ExceptionHandler(InventoryReceiptNotFoundException.class)
	public ResponseEntity<?> handleReceiptNotFound(InventoryReceiptNotFoundException exception, WebRequest request) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.localDateTime(LocalDateTime.now())
				.message(exception.getLocalizedMessage())
				.statusCode(HttpURLConnection.HTTP_NOT_MODIFIED)
				.path(request.getDescription(false)).build();
		return ResponseEntity.ok(errorMessage);
	}
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleException(RuntimeException exception, WebRequest request) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.localDateTime(LocalDateTime.now())
				.message(exception.getLocalizedMessage())
				.statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
				.path(request.getDescription(false)).build();
		return ResponseEntity.ok(errorMessage);
	}
	
}

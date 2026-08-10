package com.example.demo.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {
	private LocalDateTime localDateTime;
	private Integer statusCode;
	private String message;
	private String path;

}

package com.vinod.b2b.exceptions;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {
	private LocalDateTime localDateTime;
	private String message;
	private Integer statusCode;
	private String path;
}

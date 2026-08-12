package com.vinod.b2b.utility;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage {
	private LocalDateTime localDateTime;
	private String message;
	private String status;
	private Integer statusCode;

}

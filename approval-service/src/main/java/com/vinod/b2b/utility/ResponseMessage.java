package com.vinod.b2b.utility;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage<T> {
	private String message;
	private String status;
	private Integer statusCode;
	private T data;
}

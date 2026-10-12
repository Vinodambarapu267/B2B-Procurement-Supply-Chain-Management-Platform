package com.example.demo.utility;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage<T> {
	private Integer statuscode;
	private String status;
	private String message;
	private T data;
}

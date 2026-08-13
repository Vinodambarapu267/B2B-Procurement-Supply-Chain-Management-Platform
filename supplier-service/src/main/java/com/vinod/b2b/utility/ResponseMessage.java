package com.vinod.b2b.utility;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage<T> {
private String message;
private Integer statusCode;
private String status;
private T data;
}

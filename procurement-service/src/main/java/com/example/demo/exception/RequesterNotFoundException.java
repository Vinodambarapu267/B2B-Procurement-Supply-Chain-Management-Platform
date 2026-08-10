package com.example.demo.exception;

public class RequesterNotFoundException extends RuntimeException{

	public RequesterNotFoundException(String message) {
		super(message);
	}

}

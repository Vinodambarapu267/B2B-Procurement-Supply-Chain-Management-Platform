package com.example.demo.exceptions;

public class ManagerNotFoundException extends RuntimeException {

	public ManagerNotFoundException(String message) {
		super(message);
	}

}

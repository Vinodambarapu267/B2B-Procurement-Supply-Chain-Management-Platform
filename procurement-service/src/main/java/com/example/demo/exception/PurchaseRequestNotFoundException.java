package com.example.demo.exception;

public class PurchaseRequestNotFoundException extends RuntimeException{

	public PurchaseRequestNotFoundException(String message) {
		super(message);
	}

}

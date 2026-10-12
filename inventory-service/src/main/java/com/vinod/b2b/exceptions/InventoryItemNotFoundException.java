package com.vinod.b2b.exceptions;

public class InventoryItemNotFoundException extends RuntimeException {

	public InventoryItemNotFoundException(String message) {
		super(message);
	}

}

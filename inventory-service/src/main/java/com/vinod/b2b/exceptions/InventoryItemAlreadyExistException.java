package com.vinod.b2b.exceptions;

public class InventoryItemAlreadyExistException extends RuntimeException {

	public InventoryItemAlreadyExistException(String message) {
		super(message);
	}

}

package com.abbvie.productvisibility.exception;

@SuppressWarnings("serial")
public class DBConnectionException extends Exception{
	public DBConnectionException(String message) {
        super(message);
    }
}

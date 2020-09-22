package com.netcity.exception;

public class ServiceException extends Exception {
	private static final long serialVersionUID = 930074139747500692L;

	public ServiceException(String message) {
		super(message);
	}
}
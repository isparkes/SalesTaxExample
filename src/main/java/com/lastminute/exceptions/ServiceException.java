package com.lastminute.exceptions;

/**
 * Specific Exception class for mapping internal trouble to "500" http response.
 * 
 * @author ian
 */
public class ServiceException extends RuntimeException {
	private final static long serialVersionUID = 1L;
	
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }	

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }	
}

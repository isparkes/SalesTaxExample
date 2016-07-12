package com.lastminute.exceptions;

/**
/**
 * Specific Exception class for mapping internal downtime to "503" http response.
 * 
 * @author ian
 */
public class MonitorException extends RuntimeException {
	private final static long serialVersionUID = 1L;
	
    public MonitorException() {
        super();
    }

    public MonitorException(String message) {
        super(message);
    }	

    public MonitorException(String message, Throwable cause) {
        super(message, cause);
    }	
}

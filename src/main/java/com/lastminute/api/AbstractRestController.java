package com.lastminute.api;

import com.lastminute.exceptions.ServiceException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lastminute.exceptions.MonitorException;
import com.lastminute.utils.StringHelper;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * REST controller - boilerplate around common error handling for rest calls.
 * 
 * @author ian
 */
public abstract class AbstractRestController {
  
	private final static Logger logger = LoggerFactory.getLogger(AbstractRestController.class);

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ NotFoundException.class })
	@ResponseBody
	private ErrorInfo handleNotFound(HttpServletRequest request) {
		return new ErrorInfo(HttpStatus.NOT_FOUND.value(), request.getRequestURI(), "API path is undefined");
	}

	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	@ResponseBody
	private ErrorInfo handleWrongContentType(HttpServletRequest request, Exception exception) {
		logError(request.getRequestURL().toString(), exception);
		return new ErrorInfo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), request.getRequestURI(), exception.getLocalizedMessage()+" (Accepted: 'application/json')");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ServiceException.class, MissingServletRequestParameterException.class, JsonProcessingException.class, JsonParseException.class })
	@ResponseBody
	private ErrorInfo handleServiceError(HttpServletRequest request, Exception exception) {
		logError(request.getRequestURL().toString(), exception);
		return new ErrorInfo(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), exception);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ Throwable.class })
	@ResponseBody
	private ErrorInfo handleInternalServerError(HttpServletRequest request, Exception exception) {
		logError(request.getRequestURL().toString(), exception);
		return new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), exception);
	}
	
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({ MonitorException.class })
	@ResponseBody
	private ErrorInfo handleMonitorFailure(HttpServletRequest request, Exception exception) {
		logError(request.getRequestURL().toString(), exception);
		return new ErrorInfo(HttpStatus.SERVICE_UNAVAILABLE.value(), request.getRequestURI(), exception.getMessage());
	}
	
	private void logError(String message, Exception exception) {
		if(logger.isDebugEnabled()) {
			logger.error(message, exception);
		} else {
			logger.error("{} - {}", message, StringHelper.getShortMessage(exception));
		}
	}
	
	protected class NotFoundException extends RuntimeException {
		private final static long serialVersionUID = 1L;
		
	    public NotFoundException() {
	        super();
	    }

	    public NotFoundException(String message) {
	        super(message);
	    }	

	    public NotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }	
	}
}

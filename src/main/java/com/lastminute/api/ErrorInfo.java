package com.lastminute.api;

import com.lastminute.utils.StringHelper;

public class ErrorInfo {
	public final int status;
	public final String location;
	public final String errorMessage;

	public ErrorInfo(int status, String pathinfo, Exception exception) {
		this(status, pathinfo, StringHelper.getShortMessage(exception));
	}

	public ErrorInfo(int status, String pathinfo, String errorMessage) {
		this.status = status;
		this.location = pathinfo;
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ErrorInfo [status=" + status + ", url=" + location + ", errorMessage=" + errorMessage + "]";
	}
}

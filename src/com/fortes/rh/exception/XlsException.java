package com.fortes.rh.exception;

@SuppressWarnings("serial")
public class XlsException extends Exception
{
	public XlsException() {
		super();
	}

	public XlsException(String message, Throwable cause) {
		super(message, cause);
	}

	public XlsException(String message) {
		super(message);
	}

	public XlsException(Throwable cause) {
		super(cause);
	}
}

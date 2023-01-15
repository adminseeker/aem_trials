package com.demo.core.exceptions;


public class DemoException extends Exception {
	private static final long serialVersionUID = 1L;
	private final int errorCode;
		
		public DemoException(String message, int errorCode) {
			super(message);
			this.errorCode = errorCode;
		}
		
	    public int getErrorCode() {
	        return errorCode;
	    }
}

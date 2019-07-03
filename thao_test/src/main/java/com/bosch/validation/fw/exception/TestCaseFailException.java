package com.bosch.validation.fw.exception;

public class TestCaseFailException extends AssertionError {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	public TestCaseFailException(String message) {
		super(message);
	}
}

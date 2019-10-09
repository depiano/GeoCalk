package it.unisa.geocalk.exception;

public class LocalException extends Exception {

	private static final long serialVersionUID = 1L;

	public LocalException(String message) {
		super(message);
	}

	public LocalException(Throwable cause) {
		super(cause);
	}

	public LocalException(String message, Throwable cause) {
		super(message, cause);
	}

	public LocalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

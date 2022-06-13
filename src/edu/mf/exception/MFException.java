package edu.mf.exception;

public class MFException extends Exception {


	public MFException(String msg) {
		super(msg);
	}

	public MFException(Throwable cause) {
		super(cause);
	}

	public MFException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MFException(String msg, Throwable cause, boolean arg2, boolean arg3) {
		super(msg, cause, arg2, arg3);
	}

}

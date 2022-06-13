package edu.mf.exception;

public class MFIoException extends MFException {

	public MFIoException(String msg) {
		super(msg);
	}

	public MFIoException(Throwable cause) {
		super(cause);
	}

	public MFIoException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MFIoException(String msg, Throwable cause, boolean arg2, boolean arg3) {
		super(msg, cause, arg2, arg3);
	}

}

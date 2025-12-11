package com.livestream.Exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppException extends RuntimeException{
	private ErrorCode errorCode;

	public AppException(ErrorCode errorCode, Object... args) {
		super(String.format(errorCode.getMessage(), args));
		this.errorCode = errorCode;
	}

}

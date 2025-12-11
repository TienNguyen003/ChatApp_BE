package com.livestream.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
	UNCATEGORIZED_EXCEPTION(504, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
	INVALID(504, "Invalid message key", HttpStatus.BAD_REQUEST),
	UNAUTHENTICATED(504, "Unauthenticated", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(505, "You don't have permission", HttpStatus.FORBIDDEN),

	// username
	USERNAME_INVALID(500, "Tên đăng nhập phải có ít nhất 6 kí tự.", HttpStatus.BAD_REQUEST),
	USERNAME_NOT_EXISTED(504, "Tên đăng nhập không chính xác.", HttpStatus.NOT_FOUND),
	USER_NOT_EXISTED(504, "Tài khoản không chính xác.", HttpStatus.NOT_FOUND),
	USER_NOT_ACTIVE(503, "Tài khoản đã bị khóa.", HttpStatus.NOT_FOUND),
	USERNAME_EXISTED(502, "Tên đăng nhập đã tồn tại.", HttpStatus.BAD_REQUEST),

	// password
	PASSWORD_INVALID(500, "Mật khẩu phải có ít nhất 6 kí tự", HttpStatus.BAD_REQUEST),
	PASSWORD_NO_INCORRECT(500, "Thông tin không chính xác. Vui lòng thử lại.", HttpStatus.NOT_FOUND),
	PASSWORD_NO_MATCH(500, "Mật khẩu mới không được trùng mật khẩu cũ.", HttpStatus.NOT_FOUND),
	OLD_PASS_INCORRECT(500, "Mật khẩu cũ không chính xác. Vui lòng thử lại.", HttpStatus.NOT_FOUND),

	// quyền
	ROLE_EXISTED(502, "Quyền đã tồn tại.", HttpStatus.BAD_REQUEST),
	ROLE_NOT_EXISTED(504, "Quyền không tồn tại.", HttpStatus.BAD_REQUEST),
	
	;

	ErrorCode(int code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}

	private int code;
	private String message;
	private HttpStatusCode statusCode;
}

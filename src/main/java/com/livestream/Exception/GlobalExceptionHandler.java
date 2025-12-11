package com.livestream.Exception;

import com.livestream.DTO.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = Exception.class)
	ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception){
		ApiResponse apiResponse = new ApiResponse<>();

		apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
		apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

		return ResponseEntity.badRequest().body(apiResponse);
	}

	@ExceptionHandler(value = AppException.class)
	ResponseEntity<ApiResponse> handlingAppException(AppException exception){
		ErrorCode errorCode = exception.getErrorCode();
		String formattedMessage = exception.getMessage(); // Lấy message đã format từ exception

		ApiResponse apiResponse = new ApiResponse<>();
		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(formattedMessage); // Sử dụng message đã được format

		return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	ResponseEntity<ApiResponse> handlingAccessDExp(AccessDeniedException exception){
		ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

		return ResponseEntity.status(errorCode.getStatusCode()).body(
				ApiResponse.builder()
						.code(errorCode.getCode())
						.message(errorCode.getMessage())
						.build());
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
		String errorMessage = exception.getFieldError().getDefaultMessage();

		ApiResponse apiResponse = new ApiResponse<>();
		apiResponse.setCode(ErrorCode.INVALID.getCode()); // Giữ nguyên mã lỗi INVALID
		apiResponse.setMessage(errorMessage); // Trả về thông báo lỗi cụ thể

		return ResponseEntity.badRequest().body(apiResponse);
	}
}

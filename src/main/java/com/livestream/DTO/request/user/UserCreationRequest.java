package com.livestream.DTO.request.user;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
	@NotBlank(message = "Tên đăng nhập không được để trống")
	@Size(min = 6, message = "USERNAME_INVALID")
	@Pattern(regexp = "^[^\\s]+$", message = "Tên đăng nhập không được chứa khoảng trắng")
	String username;

	@NotBlank(message = "Mật khẩu không được để trống")
	@Size(min = 8, message = "PASSWORD_INVALID")
	@Pattern(regexp = "^[^\\s]+$", message = "Mật khẩu không được chứa khoảng trắng")
	String password;

	@NotNull(message = "ID nhân viên không được để trống")
	@Min(value = 1, message = "ID nhân viên phải lớn hơn 0")
	int employeeId;

	int status = 1;

	@NotBlank(message = "Quyền không được để trống")
	@Pattern(regexp = "^[\\p{L}]+(\\s[\\p{L}]+)*$", message = "Quyền chỉ được chứa chữ cái")
	String roleName;
}

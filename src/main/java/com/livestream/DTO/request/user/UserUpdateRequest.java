package com.livestream.DTO.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
	@NotBlank(message = "Quyền không được để trống")
	@Pattern(regexp = "^[\\p{L}]+(\\s[\\p{L}]+)*$", message = "Quyền chỉ được chứa chữ cái")
	String roleName;
}

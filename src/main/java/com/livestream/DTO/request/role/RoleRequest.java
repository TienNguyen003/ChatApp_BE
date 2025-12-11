package com.livestream.DTO.request.role;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
	@NotBlank(message = "Tên không được để trống")
	String name;

	String des;
	Set<String> permissions;
}

package com.livestream.DTO.request.token;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Pattern(regexp = "^[^\\s]+$", message = "Tên đăng nhập không được chứa khoảng trắng")
    String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Pattern(regexp = "^[^\\s]+$", message = "Mật khẩu không được chứa khoảng trắng")
    String password;
}

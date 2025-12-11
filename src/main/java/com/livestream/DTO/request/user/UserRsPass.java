package com.livestream.DTO.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRsPass {
    @NotBlank(message = "Không được để trống")
    int id;

    @NotBlank(message = "Mật khẩu mới không được để trống")
    String new_pass;

    @Email(message = "Email không đúng định")
    @NotBlank(message = "Email không được để trống")
    String email;
}

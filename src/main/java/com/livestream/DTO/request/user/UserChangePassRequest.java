package com.livestream.DTO.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserChangePassRequest {
    @NotBlank(message = "Trường này không được để trống")
    int id;

    @NotBlank(message = "Mật khẩu mới không được để trống")
    String new_pass;

    @NotBlank(message = "Mật khẩu cũ không được để trống")
    String old_pass;
}

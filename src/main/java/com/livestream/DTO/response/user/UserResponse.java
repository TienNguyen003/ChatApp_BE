package com.livestream.DTO.response.user;

import com.livestream.DTO.response.role.RoleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse  {
    String id;

    String username;

    RoleResponse role;

    String urlImage;

    int status;
}

package com.livestream.DTO.response.user;

import com.livestream.DTO.response.role.RoleResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse  {
    int id;

    String username;

    RoleResponse role;

	String email;

	String name;

	String dob;

	String phone;

	String address;
    
    String urlImage;

    int status;

}

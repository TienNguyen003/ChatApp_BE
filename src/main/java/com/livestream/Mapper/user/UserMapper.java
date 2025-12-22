package com.livestream.Mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.livestream.DTO.request.user.UserCreationRequest;
import com.livestream.DTO.request.user.UserUpdateRequest;
import com.livestream.DTO.response.user.UserResponse;
import com.livestream.Entity.user.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toUser(UserCreationRequest request);

    UserResponse toUserResponse(Users user);

    void updateUser(@MappingTarget Users user, UserUpdateRequest request);
}

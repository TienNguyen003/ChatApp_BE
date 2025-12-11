package com.livestream.Mapper.user;

import com.livestream.Entity.user.Users;
import com.livestream.DTO.request.user.UserCreationRequest;
import com.livestream.DTO.request.user.UserUpdateRequest;
import com.livestream.DTO.response.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toUser(UserCreationRequest request);

    UserResponse toUserResponse(Users user);

    @Mapping(target = "role", ignore = true)
    void updateUser(@MappingTarget Users user, UserUpdateRequest request);
}

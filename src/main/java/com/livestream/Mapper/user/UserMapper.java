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
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "dob", ignore = true)
    @Mapping(target = "urlImage", ignore = true)
    @Mapping(target = "role", ignore = true)
    Users toUser(UserCreationRequest request);

    UserResponse toUserResponse(Users user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "dob", ignore = true)
    @Mapping(target = "urlImage", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateUser(@MappingTarget Users user, UserUpdateRequest request);
}

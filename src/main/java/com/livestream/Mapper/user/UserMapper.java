package com.livestream.Mapper.user;

import com.livestream.Entity.user.User;
import com.livestream.DTO.request.user.UserCreationRequest;
import com.livestream.DTO.request.user.UserUpdateRequest;
import com.livestream.DTO.response.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "role", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}

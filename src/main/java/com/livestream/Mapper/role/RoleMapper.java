package com.livestream.Mapper.role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.livestream.DTO.request.role.RoleRequest;
import com.livestream.DTO.request.role.RoleUpdateRequest;
import com.livestream.DTO.response.role.RoleResponse;
import com.livestream.Entity.role.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "name", ignore = true)
    void updateRole(@MappingTarget Role role, RoleUpdateRequest request);
}

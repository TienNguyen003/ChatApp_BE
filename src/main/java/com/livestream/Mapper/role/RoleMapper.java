package com.livestream.Mapper.role;

import com.livestream.Entity.role.Role;
import com.livestream.DTO.request.role.RoleRequest;
import com.livestream.DTO.request.role.RoleUpdateRequest;
import com.livestream.DTO.response.role.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    void updateRole(@MappingTarget Role role, RoleUpdateRequest request);
}

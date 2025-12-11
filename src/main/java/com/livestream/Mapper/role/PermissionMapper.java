package com.livestream.Mapper.role;

import com.livestream.Entity.role.Permission;
import com.livestream.DTO.request.role.PermissionRequest;
import com.livestream.DTO.response.role.PermissionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}

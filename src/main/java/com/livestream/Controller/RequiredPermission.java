package com.livestream.Controller;

import com.livestream.Service.role.RoleService;
import com.livestream.DTO.response.role.PermissionResponse;
import com.livestream.DTO.response.role.RoleResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequiredPermission {
    RoleService roleService;

    public boolean checkPermission(String permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        GrantedAuthority firstAuthority = authentication.getAuthorities().stream()
                .findFirst()
                .orElse(null);

        String role = firstAuthority.getAuthority().replace("ROLE_", "");
        if(role.equals("NHÂN")) role = "NHÂN VIÊN";
        RoleResponse roleResponse = roleService.getRole(role);
        Set<String> permissionNames = roleResponse.getPermissions().stream()
                .map(PermissionResponse::getName)
                .collect(Collectors.toSet());
        return permissionNames.contains(permission);
    }
}

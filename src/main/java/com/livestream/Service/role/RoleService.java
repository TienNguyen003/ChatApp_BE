package com.livestream.Service.role;

import java.util.HashSet;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.livestream.DTO.request.role.RoleRequest;
import com.livestream.DTO.request.role.RoleUpdateRequest;
import com.livestream.DTO.response.role.RoleResponse;
import com.livestream.Entity.PageCustom;
import com.livestream.Entity.role.Role;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.role.RoleMapper;
import com.livestream.Repository.role.PermissionRepository;
import com.livestream.Repository.role.RoleRepository;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        if(roleRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.ROLE_EXISTED);
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public List<RoleResponse> searchAll(String name, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return roleRepository.findByName(name, pageable)
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public PageCustom getPagination(int pageNumber, String name){
        Pageable pageable = PageRequest.of(pageNumber - 1, 30);
        Page<Role> page = roleRepository.findByName(name, pageable);
        return PaginationUtil.buildPageCustom(page, pageNumber);
    }

    public RoleResponse getRole(String name){
        return roleMapper.toRoleResponse(roleRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)));
    }

    public RoleResponse updateRole(RoleUpdateRequest request, String name){
        Role role = roleRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        roleMapper.updateRole(role, request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public void delete(String name){
        roleRepository.deleteById(name);
    }
}

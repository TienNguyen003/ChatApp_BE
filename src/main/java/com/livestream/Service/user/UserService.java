package com.livestream.Service.user;

import com.livestream.Entity.PageCustom;
import com.livestream.Entity.role.Role;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.user.UserMapper;
import com.livestream.Service.EmailService;
import com.livestream.DTO.request.user.UserChangePassRequest;
import com.livestream.DTO.request.user.UserCreationRequest;
import com.livestream.DTO.request.user.UserRsPass;
import com.livestream.DTO.request.user.UserUpdateRequest;
import com.livestream.DTO.response.user.UserResponse;
import com.livestream.Repository.role.RoleRepository;
import com.livestream.Repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
	EmailService emailService;
	UserRepository userRepository;
	RoleRepository roleRepository;
	UserMapper userMapper;
	PasswordEncoder passwordEncoder;
	
	public UserResponse createUser(UserCreationRequest request) {
		if(userRepository.existsByUsername(request.getUsername()))
			throw new AppException(ErrorCode.USERNAME_EXISTED);

		Role role = roleRepository.findById(request.getRoleName())
				.orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
		Users user = userMapper.toUser(request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		user.setRole(role);
		
		return userMapper.toUserResponse(userRepository.save(user));
	}

	public List<UserResponse> getAll(){
		return userRepository.findAllUserActive().stream().map(userMapper::toUserResponse).toList();
	}

	public List<UserResponse> getUsers(String name, String username, String role, int pageNumber, int pageSize){
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return userRepository.findByName(name, username, role, pageable)
				.stream().map(userMapper::toUserResponse).toList();
	}

	public PageCustom getPagination(int pageNumber, String name, String username, String role){
		Pageable pageable = PageRequest.of(pageNumber - 1, 30);
		Page<Users> page = userRepository.findByName(name, username, role, pageable);
		return PageCustom.builder()
				.totalPages(String.valueOf(page.getTotalPages()))
				.totalItems(String.valueOf(page.getTotalElements()))
				.totalItemsPerPage(String.valueOf(page.getNumberOfElements()))
				.currentPage(String.valueOf(pageNumber))
				.build();
	}

	@PostAuthorize("returnObject.id == authentication.principal.getClaimAsString('id') or !hasRole('NHÂN')")
	public UserResponse getUser(int id) {
		return userMapper.toUserResponse(userRepository.findById(id)
				.orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTED)));
	}
	
	public UserResponse updateUser(int userId, UserUpdateRequest request) {
		Users user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTED));

		userMapper.updateUser(user, request);

		Role role = roleRepository.findById(request.getRoleName())
				.orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
		user.setRole(role);
		
		return userMapper.toUserResponse(userRepository.save(user));
	}

	public String updateStt(int id, int status) {
		Users user = userRepository.findById(id)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

		user.setStatus(status);
		userMapper.toUserResponse(userRepository.save(user));
		return "Update success";
	}
	
	public void deleteUser(int id) {
		userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTED));
		userRepository.deleteById(id);
	}

//	@PostAuthorize("returnObject.id == authentication.principal.getClaimAsString('id') or !hasRole('NHÂN')")
	public UserResponse getInfo(){
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		Users user = userRepository.findByUsername(name)
				.orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTED));

		return userMapper.toUserResponse(user);
	}

	public String rsPass(UserRsPass request) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User userLogin = (User) authentication.getPrincipal();
//
//		if(userLogin.getId().equals(request.getId()))
//			throw new AppException(ErrorCode.UNAUTHORIZED);
		Users user = userRepository.findById(request.getId())
				.orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTED));

		emailService.requestPasswordReset(user.getId(), request.getEmail(), request.getNew_pass());

		return "Please check your email";
	}

	public String changePass(UserChangePassRequest request) {
		Users user = userRepository.findById(request.getId())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		if(!passwordEncoder.matches(request.getOld_pass(), user.getPassword()))
			throw new AppException(ErrorCode.OLD_PASS_INCORRECT);
		if(passwordEncoder.matches(request.getNew_pass(), user.getPassword()))
			throw new AppException(ErrorCode.PASSWORD_NO_MATCH);
		user.setPassword(passwordEncoder.encode(request.getNew_pass()));
		userRepository.save(user);

		return "Change success";
	}

	public String updatePass(int userId, String new_pass) {
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTED));

		user.setPassword(passwordEncoder.encode(new_pass));
		userRepository.save(user);

		return "Update success";
	}
}

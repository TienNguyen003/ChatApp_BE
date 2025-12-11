package com.livestream.Controller.user;

import com.livestream.Service.user.UserService;
import com.livestream.DTO.request.user.UserChangePassRequest;
import com.livestream.DTO.request.user.UserCreationRequest;
import com.livestream.DTO.request.user.UserRsPass;
import com.livestream.DTO.request.user.UserUpdateRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.user.UserResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
	UserService userService;
	SimpMessagingTemplate messagingTemplate;

	@PreAuthorize("@requiredPermission.checkPermission('USER_ADD')")
	@PostMapping
	ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
		ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

		apiResponse.setResult(userService.createUser(request));

		return apiResponse;
	}

	// @PreAuthorize("@requiredPermission.checkPermission('USER_VIEW')")
	@GetMapping
	ApiResponse<List<UserResponse>> getUsers(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = "30") int limit,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "role", required = false) String role) {
		return ApiResponse.<List<UserResponse>>builder()
				.result(userService.getUsers(name, username, role, page, limit))
				.page(userService.getPagination(page, name, username, role))
				.build();
	}

	@PreAuthorize("@requiredPermission.checkPermission('USER_VIEW')")
	@GetMapping("/getAll")
	ApiResponse<List<UserResponse>> getUsers() {
		return ApiResponse.<List<UserResponse>>builder()
				.result(userService.getAll())
				.build();
	}

	@GetMapping("/user")
	ApiResponse<UserResponse> getUser(@RequestParam int userId) {
		return ApiResponse.<UserResponse>builder()
				.result(userService.getUser(userId))
				.build();
	}

	@GetMapping("/myInfo")
	ApiResponse<UserResponse> getInfo() {
		return ApiResponse.<UserResponse>builder()
				.result(userService.getInfo())
				.build();
	}

	@GetMapping("/update-pass")
	ApiResponse<String> updatePass(@RequestParam int userId, @RequestParam String new_pass) {
		return ApiResponse.<String>builder()
				.result(userService.updatePass(userId, new_pass))
				.build();
	}

	@PreAuthorize("@requiredPermission.checkPermission('USER_RSPASS')")
	@PutMapping("/rs-pass")
	ApiResponse<String> resetPass(@RequestBody @Valid UserRsPass request) {
		return ApiResponse.<String>builder()
				.result(userService.rsPass(request))
				.build();
	}

	// @PreAuthorize("@requiredPermission.checkPermission('USER_CPASS')")
	@PutMapping("/change-pass")
	ApiResponse<String> changePass(@RequestBody @Valid UserChangePassRequest request) {
		return ApiResponse.<String>builder()
				.result(userService.changePass(request))
				.build();
	}

	@PreAuthorize("@requiredPermission.checkPermission('USER_EDIT')")
	@PutMapping
	ApiResponse<UserResponse> updateUser(@RequestParam int userId, @RequestBody @Valid UserUpdateRequest request) {
		return ApiResponse.<UserResponse>builder()
				.result(userService.updateUser(userId, request))
				.build();
	}

	@PreAuthorize("@requiredPermission.checkPermission('USER_EDIT')")
	@PutMapping("/stt")
	ApiResponse<String> updateStt(@RequestParam int id, @RequestParam int status) {
		userService.updateStt(id, status);
		messagingTemplate.convertAndSend("/topic/updateStt", "tien");
		return ApiResponse.<String>builder()
				.result("Update success")
				.build();
	}

	@PreAuthorize("@requiredPermission.checkPermission('USER_DELETE')")
	@DeleteMapping
	ApiResponse<String> deleteUser(@RequestParam int userId) {
		userService.deleteUser(userId);
		return ApiResponse.<String>builder()
				.result("User has been deleted")
				.build();
	}
}

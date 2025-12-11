package com.livestream.Controller.user;

import com.livestream.Service.user.AuthenticationService;
import com.livestream.DTO.request.token.TokenRequest;
import com.livestream.DTO.request.token.AuthenticationRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.token.AuthenticationResponse;
import com.livestream.DTO.response.token.TokenResponse;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("${api.prefix}auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    SimpMessagingTemplate messagingTemplate;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authentication(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        var result = authenticationService.authentication(authenticationRequest);
        messagingTemplate.convertAndSend("/topic/login", "tien");
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<TokenResponse> introspect(@RequestBody @Valid TokenRequest introspectRequest)
            throws ParseException, JOSEException {
        var result = authenticationService.introspectResponse(introspectRequest);
        return ApiResponse.<TokenResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authentication(@RequestBody @Valid TokenRequest request) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody @Valid TokenRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
}

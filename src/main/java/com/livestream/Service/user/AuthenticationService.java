package com.livestream.Service.user;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.livestream.DTO.request.token.AuthenticationRequest;
import com.livestream.DTO.request.token.TokenRequest;
import com.livestream.DTO.response.token.AuthenticationResponse;
import com.livestream.DTO.response.token.TokenResponse;
import com.livestream.Entity.token.Token;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Repository.token.TokenRepository;
import com.livestream.Repository.user.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    UserRepository userRepository;
    TokenRepository tokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.expDuration}")
    protected long EXP_DURARION;

    @NonFinal
    @Value("${jwt.refreshDuration}")
    protected long REFRESH_DURARION;

    public TokenResponse introspectResponse(TokenRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return TokenResponse.builder()
                .valid(isValid)
                .build();
    }

    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenicated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        if (!authenicated)
            throw new AppException(ErrorCode.PASSWORD_NO_INCORRECT);
        if (user.getStatus() == 0)
            throw new AppException(ErrorCode.USER_NOT_ACTIVE);

        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authentication(true)
                .build();
    }

    public void logout(TokenRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken(), true);

        String jti = signToken.getJWTClaimsSet().getJWTID();
        Date expT = signToken.getJWTClaimsSet().getExpirationTime();

        Token invalidateToken = Token.builder()
                .id(jti)
                .expT(expT)
                .build();
        tokenRepository.save(invalidateToken);
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expT = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                        .toInstant().plus(REFRESH_DURARION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verifire = signedJWT.verify(jwsVerifier);

        if (!(verifire && expT.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (tokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    public AuthenticationResponse refreshToken(TokenRequest request) throws ParseException, JOSEException {
        var signedJwt = verifyToken(request.getToken(), true);

        var jti = signedJwt.getJWTClaimsSet().getJWTID();
        var expT = signedJwt.getJWTClaimsSet().getExpirationTime();

        Token invalidateToken = Token.builder()
                .id(jti)
                .expT(expT)
                .build();
        tokenRepository.save(invalidateToken);

        var username = signedJwt.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authentication(true)
                .build();
    }

    private String generateToken(Users user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwsClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("tien.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(EXP_DURARION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .claim("id", user.getId())
                // .claim("idE", user.getEmployee().getId())
                .build();
        Payload payload = new Payload(jwsClaimsSet.toJSONObject());

        JWSObject jwt = new JWSObject(header, payload);

        try {
            jwt.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwt.serialize();
        } catch (JOSEException e) {
            log.error("Can't token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Users user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add("ROLE_" + user.getRole());

        return stringJoiner.toString();
    }
}

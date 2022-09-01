package com.example.reditClone.controller;

import com.example.reditClone.dto.AuthenticationResponse;
import com.example.reditClone.dto.LoginRequest;
import com.example.reditClone.dto.RefreshTokenRequest;
import com.example.reditClone.dto.RegisterRequest;
import com.example.reditClone.security.JwtTokenService;
import com.example.reditClone.service.AuthService;
import com.example.reditClone.service.RefreshTokenService;
import com.example.reditClone.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest){
        log.info("sign up request received : "+registerRequest);
        authService.signUp(registerRequest);
        return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> accountVerification(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        log.info("Login request from :"+loginRequest);
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    loginRequest.getUsername(), loginRequest.getPassword()));
//        } catch (final BadCredentialsException ex) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        }
//
//        final UserDetails userDetails = userDetailService.loadUserByUsername(loginRequest.getUsername());
//        return new AuthenticationResponse(loginRequest.getUsername(),tokenService.generateToken(userDetails));

        return authService.login(loginRequest);
    }
    @PostMapping("refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh token deleted successfully");
    }
}

package com.example.reditClone.service;

import com.example.reditClone.dto.AuthenticationResponse;
import com.example.reditClone.dto.LoginRequest;
import com.example.reditClone.dto.RegisterRequest;
import com.example.reditClone.exception.SpringRedditException;
import com.example.reditClone.model.NotificationEmail;
import com.example.reditClone.model.User;
import com.example.reditClone.model.VerifcationToken;
import com.example.reditClone.repository.UserRepository;
import com.example.reditClone.repository.VerificationTokenRepository;
//import com.example.reditClone.security.JwtProvider;
import com.example.reditClone.security.JwtTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtProvider;

    @Transactional
    public void signUp(RegisterRequest request){

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendEmail(new NotificationEmail("Please activate your account",
                user.getEmail(),"Thank you for signing up to Spring Reddit " +
                "plese click on the below url to activate you account :" +
                "http://localhost:8080/api/auth/accountVerification/"+token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerifcationToken verifcationToken = new VerifcationToken();
        verifcationToken.setToken(token);
        verifcationToken.setUser(user);

        verificationTokenRepository.save(verifcationToken);

        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerifcationToken> verifcationToken = verificationTokenRepository.findByToken(token);
        verifcationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verifcationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerifcationToken verifcationToken) {
        String username = verifcationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User Not Fount"));
        user.setEnabled(true);
        userRepository.save(user);

    }

    public AuthenticationResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                ,loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken((UserDetails) authentication.getCredentials());
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}

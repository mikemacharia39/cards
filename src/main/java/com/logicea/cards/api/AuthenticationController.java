package com.logicea.cards.api;

import com.logicea.cards.domain.entity.User;
import com.logicea.cards.dto.LoginRequestDto;
import com.logicea.cards.dto.LoginResponseDto;
import com.logicea.cards.util.JwtUtil;
import com.logicea.cards.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordUtils passwordUtils;

    @PostMapping("/login")
    public LoginResponseDto authenticate(@Valid @RequestBody LoginRequestDto request) {
        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(), request.password()
                        )
                );

        User user = (User) authenticate.getPrincipal();

        return jwtUtil.generateResponse(user);
    }

    /**
     * This endpoint is used to encrypt a password to be used in the data.sql file
     */
    @GetMapping("/encrypt")
    @ResponseStatus(value = HttpStatus.OK)
    public String encrypt(@RequestParam String password) {
        return passwordUtils.encode(password);
    }

}

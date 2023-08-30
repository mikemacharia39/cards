package com.logicea.cards.api;

import com.logicea.cards.domain.entity.User;
import com.logicea.cards.dto.CardResponseDto;
import com.logicea.cards.dto.LoginRequestDto;
import com.logicea.cards.dto.LoginResponseDto;
import com.logicea.cards.service.UserService;
import com.logicea.cards.util.JwtUtil;
import com.logicea.cards.util.PasswordUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtUtil jwtUtil;
    private final PasswordUtils passwordUtils;
    private final UserService userService;

    @ApiOperation(value = "Generate a token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Token created", response = LoginResponseDto.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@Valid @RequestBody LoginRequestDto request) {

        User user = userService.validateUser(request);

        return ResponseEntity.ok(jwtUtil.generateResponse(user));
    }

    /**
     * This endpoint is used to encrypt a password to be used in the data.sql file
     */
    @GetMapping("/encrypt")
    public ResponseEntity<String> encrypt(@RequestParam String password) {
        return ResponseEntity.ok(passwordUtils.encode(password));
    }

}

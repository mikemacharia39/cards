package com.logicea.cards.service;

import com.logicea.cards.domain.entity.User;
import com.logicea.cards.domain.repository.UserRepository;
import com.logicea.cards.dto.LoginRequestDto;
import com.logicea.cards.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;

    public User validateUser(final LoginRequestDto request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow();

        if (!passwordUtils.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    public User loadUserByUsername(final String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow();
    }
}

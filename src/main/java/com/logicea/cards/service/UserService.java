package com.logicea.cards.service;

import com.logicea.cards.domain.entity.User;
import com.logicea.cards.domain.repository.UserRepository;
import com.logicea.cards.dto.LoginRequestDto;
import com.logicea.cards.exception.UnauthorizedProblem;
import com.logicea.cards.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;

    public User validateUser(final LoginRequestDto request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow();

        if (!passwordUtils.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedProblem("Invalid password");
        }

        return user;
    }

    public User loadUserByEmail(final String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}

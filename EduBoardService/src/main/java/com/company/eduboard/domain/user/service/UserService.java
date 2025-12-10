package com.company.eduboard.domain.user.service;

import com.company.eduboard.domain.user.dto.request.UserRequest;
import com.company.eduboard.domain.user.entity.User;
import com.company.eduboard.domain.user.repository.UserRepository;
import com.company.eduboard.global.error.exception.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateEmailException(userRequest.getEmail());
        }

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        User user = User.of(userRequest.getEmail(), encodedPassword, userRequest.getNickname());
        return userRepository.save(user);
    }
}

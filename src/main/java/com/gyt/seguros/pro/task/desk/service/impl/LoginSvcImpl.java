package com.gyt.seguros.pro.task.desk.service.impl;

import com.gyt.seguros.pro.task.desk.dao.repository.UserRepository;
import com.gyt.seguros.pro.task.desk.dto.UserLoginRequest;
import com.gyt.seguros.pro.task.desk.model.User;
import com.gyt.seguros.pro.task.desk.service.LoginSvc;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginSvcImpl implements LoginSvc {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginSvcImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Optional<User> authenticate(UserLoginRequest request) {

        if (request.getUsername() == null || request.getUsername().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            return Optional.empty();
        }

        String username = request.getUsername().trim();
        String rawPassword = request.getPassword();

        Optional<User> userOptional = userRepository.findByUsername(username);


        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        if (passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }
}
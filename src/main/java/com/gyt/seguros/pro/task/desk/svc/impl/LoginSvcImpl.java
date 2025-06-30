package com.gyt.seguros.pro.task.desk.svc.impl;

import com.gyt.seguros.pro.task.desk.dal.repository.UserRepository;
import com.gyt.seguros.pro.task.desk.svc.dto.UserLoginRequest;
import com.gyt.seguros.pro.task.desk.dal.model.User;
import com.gyt.seguros.pro.task.desk.svc.LoginSvc;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class LoginSvcImpl implements LoginSvc {

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
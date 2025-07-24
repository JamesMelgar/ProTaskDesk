package com.gyt.seguros.pro.task.desk.svc.login;

import com.gyt.seguros.pro.task.desk.svc.dto.UserLoginRequest;
import com.gyt.seguros.pro.task.desk.svc.user.User;
import com.gyt.seguros.pro.task.desk.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class LoginSvcImpl implements LoginSvc {

    @Autowired
    private UserRepositoryLogin userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<User> authenticate(UserLoginRequest request) {

        try {
            if (request == null) {
                return Optional.empty();
            }
            ValidationUtil.validateNotNullOrEmpty(request, "username", "password");
        } catch (IllegalArgumentException e) {
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

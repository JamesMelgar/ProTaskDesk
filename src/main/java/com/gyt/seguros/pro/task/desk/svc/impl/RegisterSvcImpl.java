package com.gyt.seguros.pro.task.desk.svc.impl;

import com.gyt.seguros.pro.task.desk.dal.repository.UserRepository;
import com.gyt.seguros.pro.task.desk.svc.dto.UserRegistrationRequest;
import com.gyt.seguros.pro.task.desk.dal.model.User;
import com.gyt.seguros.pro.task.desk.svc.RegisterSvc;

import com.gyt.seguros.pro.task.desk.svc.exceptions.DuplicateEmailException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.DuplicateUsernameException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.InvalidRegistrationDataException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.UserRegistrationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class RegisterSvcImpl implements RegisterSvc {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegisterSvcImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean registerUser(UserRegistrationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String fullName = request.getFullName();
        String email = request.getEmail();

        if (username == null || username.trim().isEmpty() ||
                password == null || password.isEmpty() ||
                fullName == null || fullName.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            throw new InvalidRegistrationDataException("Todos los campos (usuario, contraseña, nombre completo, email) son obligatorios.");
        }
        if (username.length() < 3) {
            throw new InvalidRegistrationDataException("El usuario debe tener al menos 3 caracteres.");
        }
        if (password.length() < 6) {
            throw new InvalidRegistrationDataException("La contraseña debe tener al menos 6 caracteres.");
        }

        Optional<User> existingUserByUsername = userRepository.findByUsername(username);
        if (existingUserByUsername.isPresent()) {
            throw new DuplicateUsernameException(username);
        }

        Optional<User> existingUserByEmail = userRepository.findByEmail(email);
        if (existingUserByEmail.isPresent()) {
            throw new DuplicateEmailException(email);
        }

        String hashedPassword = passwordEncoder.encode(password);

        User newUser = new User();
        newUser.setUsername(username.trim());
        newUser.setPasswordHash(hashedPassword);
        newUser.setFullName(fullName.trim());
        newUser.setEmail(email.trim());

        try {
            userRepository.save(newUser);
            return true;
        } catch (Exception e) {
            throw new UserRegistrationException("Error al registrar el usuario debido a un problema interno.", e);
        }
    }
}

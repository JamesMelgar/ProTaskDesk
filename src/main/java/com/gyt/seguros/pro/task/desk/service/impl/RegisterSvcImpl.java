package com.gyt.seguros.pro.task.desk.service.impl;

import com.gyt.seguros.pro.task.desk.dao.repository.UserRepository;
import com.gyt.seguros.pro.task.desk.model.User;

import com.gyt.seguros.pro.task.desk.service.RegisterSvc;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterSvcImpl implements RegisterSvc {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegisterSvcImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public boolean registerUser(String username, String password, String fullName, String email) {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.isEmpty() ||
                fullName == null || fullName.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos (usuario, contraseña, nombre completo, email) son obligatorios.");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("El usuario debe tener al menos 3 caracteres.");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }


        Optional<User> existingUserByUsername = userRepository.findByUsername(username);
        if (existingUserByUsername.isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario '" + username + "' ya está en uso.");
        }

        Optional<User> existingUserByEmail = userRepository.findByEmail(email);
        if (existingUserByEmail.isPresent()) {
            throw new IllegalArgumentException("El email '" + email + "' ya está registrado.");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User newUser = new User();
        newUser.setUsername(username.trim());
        newUser.setPasswordHash(hashedPassword);
        newUser.setFullName(fullName.trim());
        newUser.setEmail(email.trim());

        try {
            userRepository.save(newUser);
            System.out.println("RegisterSvc: Usuario '" + username + "' registrado con éxito en la base de datos.");
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar el usuario en la base de datos: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al registrar el usuario debido a un problema interno.");
        }
    }
}
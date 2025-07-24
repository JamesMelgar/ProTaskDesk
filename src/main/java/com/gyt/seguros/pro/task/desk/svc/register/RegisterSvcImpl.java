package com.gyt.seguros.pro.task.desk.svc.register;

import com.gyt.seguros.pro.task.desk.svc.dto.UserRegistrationRequest;
import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions.DuplicateEmailException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions.DuplicateUsernameException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions.InvalidRegistrationDataException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions.UserRegistrationException;
import com.gyt.seguros.pro.task.desk.svc.user.User;
import com.gyt.seguros.pro.task.desk.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
class RegisterSvcImpl implements RegisterSvc {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public boolean registerUser(UserRegistrationRequest request) {

        if (request == null) {
            throw new InvalidRegistrationDataException("Los datos de registro de usuario no pueden ser nulos.");
        }

        try {
            ValidationUtil.validateNotNullOrEmpty(request, "username", "password", "fullName", "email");
        } catch (IllegalArgumentException e) {
            throw new InvalidRegistrationDataException("Todos los campos (usuario, contraseña, nombre completo, email) son obligatorios: " + e.getMessage());
        }

        String username = request.getUsername().trim();
        String password = request.getPassword();
        String fullName = request.getFullName().trim();
        String email = request.getEmail().trim();

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
        newUser.setUsername(username);
        newUser.setPasswordHash(hashedPassword);
        newUser.setFullName(fullName);
        newUser.setEmail(email);

        try {
            userRepository.save(newUser);
            return true;
        } catch (Exception e) {
            throw new UserRegistrationException("Error al registrar el usuario debido a un problema interno.", e);
        }
    }

}

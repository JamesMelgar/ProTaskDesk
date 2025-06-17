package com.gyt.seguros.pro.task.desk.service;

import com.gyt.seguros.pro.task.desk.dto.UserLoginRequest;
import com.gyt.seguros.pro.task.desk.model.User;

import java.util.Optional;

public interface LoginSvc {

    Optional<User> authenticate(UserLoginRequest request);

}

package com.gyt.seguros.pro.task.desk.svc.login;

import com.gyt.seguros.pro.task.desk.svc.dto.UserLoginRequest;
import com.gyt.seguros.pro.task.desk.svc.user.User;

import java.util.Optional;

public interface LoginSvc {

    Optional<User> authenticate(UserLoginRequest request);

}

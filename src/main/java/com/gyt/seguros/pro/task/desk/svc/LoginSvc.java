package com.gyt.seguros.pro.task.desk.svc;

import com.gyt.seguros.pro.task.desk.svc.dto.UserLoginRequest;
import com.gyt.seguros.pro.task.desk.dal.model.User;

import java.util.Optional;

public interface LoginSvc {

    Optional<User> authenticate(UserLoginRequest request);

}

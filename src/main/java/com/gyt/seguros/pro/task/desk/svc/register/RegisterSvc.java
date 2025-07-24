package com.gyt.seguros.pro.task.desk.svc.register;

import com.gyt.seguros.pro.task.desk.svc.dto.UserRegistrationRequest;

public interface RegisterSvc {

    boolean registerUser(UserRegistrationRequest request);
}

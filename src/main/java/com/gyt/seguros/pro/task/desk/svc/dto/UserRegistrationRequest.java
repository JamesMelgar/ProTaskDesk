package com.gyt.seguros.pro.task.desk.svc.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class UserRegistrationRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String email;
}

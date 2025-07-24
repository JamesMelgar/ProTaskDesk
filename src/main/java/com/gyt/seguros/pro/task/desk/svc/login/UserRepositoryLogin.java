package com.gyt.seguros.pro.task.desk.svc.login;


import com.gyt.seguros.pro.task.desk.svc.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepositoryLogin extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}

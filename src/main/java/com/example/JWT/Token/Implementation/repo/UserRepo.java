package com.example.JWT.Token.Implementation.repo;

import com.example.JWT.Token.Implementation.entity.UserLoginRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserLoginRequest,Long> {

    Optional<UserLoginRequest> findByUsername(String username);


}

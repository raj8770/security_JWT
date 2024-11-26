package com.example.JWT.Token.Implementation.repo;

import com.example.JWT.Token.Implementation.entity.UserLoginRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserLoginRequest,Long> {


}

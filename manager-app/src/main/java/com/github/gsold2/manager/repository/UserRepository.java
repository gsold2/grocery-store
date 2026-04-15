package com.github.gsold2.manager.repository;

import com.github.gsold2.manager.entity.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<StoreUser, Integer> {

    Optional<StoreUser> findUserByName(String userName);
}
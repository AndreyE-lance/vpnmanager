package com.elantsev.vpnmanager.repository;

import com.elantsev.vpnmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
}

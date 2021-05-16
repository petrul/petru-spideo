package com.spideo.auctions.repo;

import com.spideo.auctions.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByName(String name);
    
}

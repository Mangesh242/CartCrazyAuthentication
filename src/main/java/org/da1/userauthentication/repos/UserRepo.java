package org.da1.userauthentication.repos;

import org.da1.userauthentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User save(User user);
    Optional<User> findByEmail(String email);
}

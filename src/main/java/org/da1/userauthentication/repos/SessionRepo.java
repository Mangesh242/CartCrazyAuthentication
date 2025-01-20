package org.da1.userauthentication.repos;

import org.da1.userauthentication.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SessionRepo extends JpaRepository<Session, Long> {
    Session save(Session session);

    Optional<Session> findByTokenAndUserId(String token, Long userId);
}

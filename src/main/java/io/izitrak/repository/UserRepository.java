package io.izitrak.repository;

import io.izitrak.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findUserByEmail(String email);
    Optional<User> findByCompanyName(String companyName);
}

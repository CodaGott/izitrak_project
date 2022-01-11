package io.izitrak.repository;

import io.izitrak.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByFirstNameOrLastNameOrEmail(String firstName, String lastName, String email);

}

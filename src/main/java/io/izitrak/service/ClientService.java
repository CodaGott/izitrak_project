package io.izitrak.service;

import io.izitrak.domain.dto.UserDto;
import io.izitrak.domain.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    Client addClient (Long userId, UserDto userDto);
    Client updateClient(Long clientId);
    Client getClientByNameOrUsernameOrEmail(String username, String firstName, String lastName, String email);
    List<Client> getAllClients();
    List<Client> getAllAboutToExpireClient();
    List<Client> getAllExpiredClient();
    List<Client>getAllActiveClients();
    void deleteClient(Long clientId);

}

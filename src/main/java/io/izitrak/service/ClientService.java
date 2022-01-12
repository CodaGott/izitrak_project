package io.izitrak.service;

import io.izitrak.domain.dto.ClientDto;
import io.izitrak.domain.model.Client;
import io.izitrak.exception.ClientException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ClientService {
    Client addClient (Long userId, ClientDto clientDto) throws ClientException;
    Client updateClient(ClientDto newClientInfo);
    Client getClientByNameOrUsernameOrEmail(String firstName, String lastName, String email);
    List<Client> getAllClients();
    List<Client> getAllAboutToExpireClient();
    List<Client> getAllExpiredClient();
    List<Client>getAllActiveClients();
    void deleteClient(Long clientId);

}

package io.izitrak.service;

import io.izitrak.domain.dto.UserDto;
import io.izitrak.domain.model.Client;

import java.util.List;

public class ClientServiceImpl implements ClientService{


    @Override
    public Client addClient(Long userId, UserDto userDto) {
        return null;
    }

    @Override
    public Client updateClient(Long clientId) {
        return null;
    }

    @Override
    public Client getClientByNameOrUsernameOrEmail(String username, String firstName, String lastName, String email) {
        return null;
    }

    @Override
    public List<Client> getAllClients() {
        return null;
    }

    @Override
    public List<Client> getAllAboutToExpireClient() {
        return null;
    }

    @Override
    public List<Client> getAllExpiredClient() {
        return null;
    }

    @Override
    public List<Client> getAllActiveClients() {
        return null;
    }

    @Override
    public void deleteClient(Long clientId) {

    }
}

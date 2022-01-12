package io.izitrak.service;

import io.izitrak.domain.dto.ClientDto;
import io.izitrak.domain.model.Client;
import io.izitrak.exception.ClientException;
import io.izitrak.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Client addClient(Long userId, ClientDto clientDto) throws ClientException {
        Client client = new Client();
        Optional<Client> optionalClient = clientRepository.findByEmail(clientDto.getEmail());
        if (optionalClient.isPresent()){
            throw new ClientException("Client with email already exist");
        }
        modelMapper.map(clientDto, client);
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(ClientDto newClientInfo) {
//        Optional<Client> clientToUpdate = clientRepository.findByEmail()
        Client clientToUpdate = new Client();
        modelMapper.map(newClientInfo, clientToUpdate);

        return clientRepository.save(clientToUpdate);
    }

    // This another way an update can be implemented
    public Client updateClient(ClientDto newClientInfo, Long clientId) throws ClientException {
        Client clientToUpdate = findClientById(clientId);
//         = new Client();
        modelMapper.map(newClientInfo, clientToUpdate);

        return clientRepository.save(clientToUpdate);
    }

    private Client findClientById(Long clientId) throws ClientException {

        if (clientRepository.findById(clientId).isEmpty()){
            throw new ClientException("Client with Id does not exist");
        }else {
            return clientRepository.findById(clientId).orElseThrow();
        }
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

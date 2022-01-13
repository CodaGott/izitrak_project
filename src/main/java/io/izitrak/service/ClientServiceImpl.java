package io.izitrak.service;

import io.izitrak.domain.dto.ClientDto;
import io.izitrak.domain.model.Client;
import io.izitrak.exception.ClientException;
import io.izitrak.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Client addClient(Long userId, ClientDto clientDto) throws ClientException {
        Client client = new Client();
        Optional<Client> optionalClient = clientRepository.findByEmail(clientDto.getEmail());
        if (optionalClient.isPresent()) {
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

        if (clientRepository.findById(clientId).isEmpty()) {
            throw new ClientException("Client with Id does not exist");
        } else {
            return clientRepository.findById(clientId).orElseThrow();
        }
    }

    @Override
    public Client getClientByNameOrUsernameOrEmail(String firstName, String lastName, String email) {
        return (Client) clientRepository.findByFirstNameOrLastNameOrEmail(firstName, lastName, email);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> getAllAboutToExpireClient() {
        List<Client> aboutToExpireClients = clientRepository.findAll();

        for (Client client : aboutToExpireClients) {
            LocalDate dateBefore = client.getStartDate();
            LocalDate dateAfter = client.getExpiringDate();
            long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
            log.info("days: {}", noOfDaysBetween);
            if (noOfDaysBetween == client.getPaymentReminderDate()) {
                aboutToExpireClients.add(client);
            }
        }
        return aboutToExpireClients;
    }

        @Override
        public List<Client> getAllExpiredClient () {
            List<Client> expiredClients = clientRepository.findAll();

            for (Client client : expiredClients){
                LocalDate dateBefore = client.getStartDate();
                LocalDate dateAfter = client.getExpiringDate();
                long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
                if (noOfDaysBetween <= 0){
                    expiredClients.add(client);
                }
            }
            return expiredClients;
        }

        @Override
        public List<Client> getAllActiveClients () {
        List<Client> activeClients = clientRepository.findAll();

        for (Client client : activeClients){
            LocalDate dateBefore = client.getStartDate();
            LocalDate dateAfter = client.getExpiringDate();
            long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
            if (noOfDaysBetween > 0){
                activeClients.add(client);
            }
        }
            return activeClients;
        }

        @Override
        public void deleteClient (Long clientId) throws ClientException {
        Client clientToDelete = findClientById(clientId);
        clientRepository.delete(clientToDelete);

        }

}

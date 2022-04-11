package io.izitrak.service;

import io.izitrak.domain.dto.ClientDto;
import io.izitrak.domain.model.Client;
import io.izitrak.domain.model.User;
import io.izitrak.exception.ClientException;
import io.izitrak.repository.ClientRepository;
import io.izitrak.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    public ClientServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public Client addClient(Long userId, ClientDto clientDto) throws ClientException {
        Client client = new Client();
        Optional<Client> optionalClient = clientRepository.findByEmail(clientDto.getEmail());
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalClient.isPresent()) {
            throw new ClientException("Client with email already exist");
        }
        if (optionalUser.isPresent()){
            modelMapper.map(clientDto, client);
            User user = optionalUser.get();
            Client savedClient = clientRepository.save(client);
            user.addClients(savedClient);
            userRepository.save(user);
            return savedClient;
        }
        else {
            throw new NoSuchElementException("User does not exist");
        }
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

    public Client getClientByName(String firstName){
        return clientRepository.findByFirstName(firstName)
                .orElseThrow();
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> getAllAboutToExpireClient() {
        List<Client> aboutToExpireClients = clientRepository.findAll();

        for (Iterator<Client> clientIterator = aboutToExpireClients.iterator(); clientIterator.hasNext();) {
            Client client = clientIterator.next();

            LocalDate dateBefore = LocalDate.now();
            LocalDate dateAfter = client.getExpiringDate();
            long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
            log.info("number of days between them: dayBefore: {} dayAfter: {} noOfDayBetween: {}", dateBefore, dateAfter, noOfDaysBetween);
            if (noOfDaysBetween != client.getPaymentReminderDate()) {
                clientIterator.remove();
            }
        }
        return aboutToExpireClients;
    }

        @Override
        public List<Client> getAllExpiredClient () {
            List<Client> expiredClients = clientRepository.findAll();

//            for (Client client : expiredClients){
//                LocalDate dateBefore = client.getStartDate();
//                LocalDate dateAfter = client.getExpiringDate();
//                long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
//                if (noOfDaysBetween <= 0){
//                    expiredClients.add(client);
//                }
//            }

            return expiredClients.stream().filter(client -> client.getExpiringDate()
                    .isBefore(LocalDate.now()) || client.getExpiringDate().isEqual(LocalDate.now())).collect(Collectors.toList());
//            return expiredClients;
        }

        @Override
        public List<Client> getAllActiveClients () {
//        List<Client> activeClients = new ArrayList<>();
        List<Client> allClients = clientRepository.findAll();

//            for (Client client : allClients) {
//                LocalDate dateBefore = client.getStartDate();
//                LocalDate dateAfter = client.getExpiringDate();
//                long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
//                if (noOfDaysBetween > 0) {
//                    allClients.add(client);
//                }
//            }
//            return allClients;

            return allClients.stream().filter(client -> client.getExpiringDate()
                    .isAfter(LocalDate.now())).collect(Collectors.toList());
        }

        @Override
        public void deleteClient (Long clientId) throws ClientException {
        Client clientToDelete = findClientById(clientId);
        clientRepository.delete(clientToDelete);

        }

}

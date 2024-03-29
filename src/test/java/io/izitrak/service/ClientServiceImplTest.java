package io.izitrak.service;

import io.izitrak.domain.dto.ClientDto;
import io.izitrak.domain.model.Client;
import io.izitrak.domain.model.User;
import io.izitrak.exception.ClientException;
import io.izitrak.repository.ClientRepository;
import io.izitrak.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    ClientServiceImpl clientService = new ClientServiceImpl(modelMapper);

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientRepository clientRepository;



    @BeforeEach
    void setUp() {
    }

    @Test
    void addClient() throws ClientException {
        User user = new User();
        user.setEmail("user@mail.com");
        user.setLastName("lName");

        ClientDto clientDto = new ClientDto();
        clientDto.setEmail("me@mail.com");
        clientDto.setClientContactNumber("090028");
        clientDto.setFirstName("Emma");
        Long userId = 4L;


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        clientService.addClient(userId, clientDto);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);

        verify(clientRepository, Mockito.times(1)).save(clientCaptor.capture());
        verify(userRepository, Mockito.times(1)).save(userCaptor.capture());

        Client client = clientCaptor.getValue();
        User capturedUser = userCaptor.getValue();

        assertThat(client.getEmail()).isEqualTo(clientDto.getEmail());
        assertThat(capturedUser.getClients()).isNotEmpty();
//        assertEquals(userId, client.getUser().getId());
    }

    @Test
    void updateClient() {
        Client client = new Client();
        client.setId(9L);
        client.setFirstName("fName");
        client.setStartDate(LocalDate.of(2022, 3, 3));

        ClientDto clientDto = new ClientDto();
        clientDto.setStartDate(LocalDate.of(2022, 4, 4));

        clientService.updateClient(clientDto);
        ArgumentCaptor<Client> argumentCaptor = ArgumentCaptor.forClass(Client.class);

        verify(clientRepository, times(1)).save(argumentCaptor.capture());

        Client capturedClient = argumentCaptor.getValue();

        assertEquals(capturedClient.getStartDate(), clientDto.getStartDate());
    }

    @Test
    void testUpdateClient() throws ClientException {
        Client client = new Client();
        client.setId(9L);
        client.setFirstName("fName");
        client.setStartDate(LocalDate.of(2022, 3, 3));

        ClientDto clientDto = new ClientDto();
        clientDto.setStartDate(LocalDate.of(2022, 4, 4));

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        clientService.updateClient(clientDto, 9L);
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);

        verify(clientRepository, times(1)).save(clientArgumentCaptor.capture());

        Client captured = clientArgumentCaptor.getValue();


        assertEquals(captured.getStartDate(), clientDto.getStartDate());
    }

    @Test
    void getClientByNameOrUsernameOrEmail() {
        // Given a list of clients
        Client client = new Client();

        client.setFirstName("Client3 first name");

        when(clientRepository.findByFirstName(anyString())).thenReturn(Optional.of(client));
        Client result = clientService.getClientByName(client.getFirstName());

        // assert that
        assertEquals(client.getFirstName(), result.getFirstName());
    }

    @Test
    void getAllClients() {
        // Given a list of clients
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();

        LocalDate signUpDate = LocalDate.of(2021, 1, 10);

        LocalDate expiresOn = LocalDate.of(2021, 12, 10);


        client1.setFirstName("Client1 first name");
        client1.setStartDate(signUpDate);
        client1.setExpiringDate(expiresOn);


        client2.setFirstName("Client2 first name");
        client2.setStartDate(signUpDate);
        client2.setExpiringDate(signUpDate.minusDays(1L));


        client3.setFirstName("Client3 first name");
        client3.setStartDate(LocalDate.of(2021, 1, 10));
        client3.setExpiringDate(LocalDate.of(2021, 9, 5));

        List<Client> myClients = new ArrayList<>();
        myClients.add(client1);
        myClients.add(client2);
        myClients.add(client3);

        when(clientRepository.findAll()).thenReturn(myClients);

        // assert that
        assertEquals(3, clientService.getAllClients().size());
    }

    @Test
    void getAllAboutToExpireClient() {
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();

        LocalDate signUpDate = LocalDate.of(2021, 1, 10);
        LocalDate expiresOn = LocalDate.of(2022, 4, 18);

        client1.setFirstName("Client1 first name");
        client1.setStartDate(signUpDate);
        client1.setExpiringDate(expiresOn);
        client1.setPaymentReminderDate(7);


        client2.setFirstName("Client2 first name");
        client2.setStartDate(signUpDate);
        client2.setExpiringDate(expiresOn);
        client2.setPaymentReminderDate(7);

        client3.setFirstName("Client3 first name");
        client3.setStartDate(LocalDate.of(2021, 1, 10));
        client3.setExpiringDate(LocalDate.of(2022, 4, 30));
        client3.setPaymentReminderDate(7);

        LocalDate a = LocalDate.of(2012, 6, 30);
        LocalDate b = LocalDate.of(2012, 7, 1);
        a.isBefore(b); // == true
        a.isBefore(a); // == false
        b.isBefore(a); // == false


        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);

        when(clientRepository.findAll()).thenReturn(clients);

        assertEquals(2, clientService.getAllAboutToExpireClient().size());
    }

    @Test
    void getAllExpiredClient() {
        // Given a list of clients
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();

        LocalDate signUpDate = LocalDate.of(2021, 1, 10);

        LocalDate expiresOn = LocalDate.of(2021, 12, 10);


        client1.setFirstName("Client1 first name");
        client1.setStartDate(signUpDate);
        client1.setExpiringDate(expiresOn);


        client2.setFirstName("Client2 first name");
        client2.setStartDate(signUpDate);
        client2.setExpiringDate(expiresOn);


        client3.setFirstName("Client3 first name");
        client3.setStartDate(LocalDate.of(2021, 1, 10));
        client3.setExpiringDate(LocalDate.of(2021, 9, 5));

        List<Client> myClients = new ArrayList<>();
        myClients.add(client1);
        myClients.add(client2);
        myClients.add(client3);

        when(clientRepository.findAll()).thenReturn(myClients);

        // assert that
        assertEquals(3, clientService.getAllExpiredClient().size());
    }

    @Test
    void getAllActiveClients() {
        Client client = new Client();
        Client client1 = new Client();
        Client client2 = new Client();

        // EXPIRED CLIENT
        client.setStartDate(LocalDate.of(2021, 3, 1));
        client.setExpiringDate(LocalDate.of(2021, 12, 3));

        // ACTIVE CLIENT
        client1.setStartDate(LocalDate.of(2022, 1, 1));
        client1.setExpiringDate(LocalDate.of(2022, 5, 1));

        // EXPIRED CLIENT
        client2.setStartDate(LocalDate.of(2022, 1, 1));
        client2.setExpiringDate(LocalDate.of(2022, 3, 1));

        List<Client> clients = new ArrayList<>();
        clients.add(client);
        clients.add(client1);
        clients.add(client2);

        when(clientRepository.findAll()).thenReturn(clients);

        assertEquals(1, clientService.getAllActiveClients().size());
    }

    @Test
    void deleteClient() throws ClientException {
        //Given
        Client client1 = new Client();

        client1.setFirstName("John Doe");
        client1.setEmail("client1@email.com");
        client1.setId(9L);

        when(clientRepository.findById(client1.getId())).thenReturn(Optional.of(client1));
        clientService.deleteClient(client1.getId());

        verify(clientRepository).delete(client1);
    }
}
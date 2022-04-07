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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    }

    @Test
    void testUpdateClient() {
    }

    @Test
    void getClientByNameOrUsernameOrEmail() {
    }

    @Test
    void getAllClients() {
    }

    @Test
    void getAllAboutToExpireClient() {
    }

    @Test
    void getAllExpiredClient() {
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

        verify(clientRepository).deleteById(client1.getId());
    }
}
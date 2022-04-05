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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
    }

    @Test
    void deleteClient() {
    }
}
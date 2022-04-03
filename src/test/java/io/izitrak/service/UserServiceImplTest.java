package io.izitrak.service;

import io.izitrak.domain.dto.UserDto;
import io.izitrak.domain.model.Address;
import io.izitrak.domain.model.User;
import io.izitrak.exception.UserException;
import io.izitrak.repository.ClientRepository;
import io.izitrak.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    UserServiceImpl userService = new UserServiceImpl(modelMapper);

    @Mock
    ClientRepository clientRepository;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testUserAccountCanBeCreated() throws UserException {
        UserDto userDto = new UserDto();
        Address address = new Address();

        address.setCountry("Nigeria");
        address.setCity("Lagos");
        address.setStreet("Abolaji");
        address.setStreetNumber("45");
        address.setZip("8897");

        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("Email@me.com");

        userService.addUser(userDto);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getFirstName()).isEqualTo(userDto.getFirstName());
    }

    @Test
    void testUserAccountCanBeUpdated() throws UserException {
        User user = new User();
        user.setFirstName("fName");
        user.setLastName("lName");
        Long userId = 1L;

        UserDto newUpdate = new UserDto();
        newUpdate.setFirstName("John");
        newUpdate.setEmail("me@mail.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.updateUserInfo(newUpdate, userId);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        verify(userRepository, times(1)).save(captor.capture());

        User capturedUser = captor.getValue();

        assertThat(capturedUser.getFirstName()).isEqualTo(newUpdate.getFirstName());
        assertThat(capturedUser.getEmail()).isEqualTo(newUpdate.getEmail());

    }

    @Test
    void testUserAccountCanBeDeleted() throws UserException {
        User user = new User();
        user.setEmail("me@mail.com");
        user.setId(8L);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.deleteUser(user.getId());
        verify(userRepository).delete(user);
    }

    @Test
    void testUsersCanBeFound(){

    }

    @Test
    void testUserCanBeFoundUsingEmail(){

    }
}
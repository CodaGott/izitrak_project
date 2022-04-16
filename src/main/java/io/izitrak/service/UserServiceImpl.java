package io.izitrak.service;

import io.izitrak.domain.dto.UserDto;
import io.izitrak.domain.model.User;
import io.izitrak.exception.UserException;
import io.izitrak.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@NoArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public User addUser(UserDto userDto) throws UserException {
        User user = new User();
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()){
            log.info("User with email already exists");
            throw new UserException("User with email already exist");
        }
        modelMapper.map(userDto, user);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving User {}", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User updateUserInfo(UserDto newUserDetail, Long userId) throws UserException {
        User userToUpdate = findUserById(userId);
        modelMapper.map(newUserDetail, userToUpdate);
        log.info("Updating user info");
        return userRepository.save(userToUpdate);
    }

    private User findUserById(Long userId) throws UserException {
        if (userRepository.findById(userId).isEmpty()){
            log.info("User with {} id, not found", userId);
            throw new UserException("User with Id does not exist");
        }
        return userRepository.findById(userId).get();
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) throws UserException {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()){
            log.info("User with provided email doesn't exist");
            throw new UserException("User with given email not found");
        }
        return user.get();
    }

    @Override
    public void deleteUser(Long userId) throws UserException {
        Optional<User> userToDelete = userRepository.findById(userId);
        if (userToDelete.isEmpty()){
            log.info("User with Id not found");
            throw new UserException("User with id does not exist");
        }
        log.info("Deleting user");
        userRepository.delete(userToDelete.get());
    }
}

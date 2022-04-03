package io.izitrak.service;

import io.izitrak.domain.dto.UserDto;
import io.izitrak.domain.model.User;
import io.izitrak.exception.UserException;
import io.izitrak.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
@NoArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public User addUser(UserDto userDto) throws UserException {
        User user = new User();
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()){
            throw new UserException("User with username already exist");
        }
        modelMapper.map(userDto, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUserInfo(UserDto newUserDetail, Long userId) throws UserException {
        User userToUpdate = findUserById(userId);
        modelMapper.map(newUserDetail, userToUpdate);
        return userRepository.save(userToUpdate);
    }

    private User findUserById(Long userId) throws UserException {
        if (userRepository.findById(userId).isEmpty()){
            throw new UserException("User with Id does not exist");
        }
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public void deleteUser(Long userId) throws UserException {
        User userToDelete = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
        userRepository.delete(userToDelete);
    }
}

package io.izitrak.service;

import io.izitrak.domain.dto.UserDto;
import io.izitrak.domain.model.User;
import io.izitrak.exception.UserException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User addUser(UserDto userDto) throws UserException;
    User updateUserInfo(UserDto newUserDetail, Long userId) throws UserException;
    List<User> getAllUsers();
    User findUserByEmail(String email);
    void deleteUser(Long userId) throws UserException;
}

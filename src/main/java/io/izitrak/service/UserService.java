package io.izitrak.service;

import io.izitrak.domain.dto.UserDto;
import io.izitrak.domain.model.User;
import io.izitrak.exception.UserException;

import java.util.List;


public interface UserService {
    User addUser(UserDto userDto) throws UserException;
    User updateUserInfo(UserDto newUserDetail, Long userId) throws UserException;
    List<User> getAllUsers();
    User findUserByEmail(String email) throws UserException;
    void deleteUser(Long userId) throws UserException;
}

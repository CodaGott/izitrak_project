package io.izitrak.contoller;

import io.izitrak.domain.dto.UserDto;
import io.izitrak.exception.UserException;
import io.izitrak.payload.ApiResponse;
import io.izitrak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/sign-up")
    public ResponseEntity<?> createUserAccount(@RequestBody UserDto userDto){
        try {
            userService.addUser(userDto);
            return new ResponseEntity<>(new ApiResponse(true, "Account created successfully"), HttpStatus.CREATED);
        }catch (UserException e){
            return new ResponseEntity<>(new ApiResponse(false, "Account not created."), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.FOUND);
    }

    @GetMapping("/find-user/{email}")
    // TODO: This endpoint should be accessed by admin only.
    public ResponseEntity<?> findUserByEmail(@PathVariable String email) throws UserException {
        try {
            userService.findUserByEmail(email);
            return new ResponseEntity<>(new ApiResponse(true, "User with email found"), HttpStatus.FOUND);
        }catch (UserException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update-info/{userId}")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserDto userDto, @PathVariable Long userId){
        try {
            userService.updateUserInfo(userDto, userId);
            return new ResponseEntity<>(new ApiResponse(true, "User info updated"), HttpStatus.OK);
        }catch (UserException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/get-by-id/{userId}")
//    // TODO: This endpoint should be accessed by admin only.
//    public ResponseEntity<?> getById(@PathVariable Long userId){
//        try {
//            userService.
//        }
//    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(new ApiResponse(true, "User deleted successfully"), HttpStatus.NO_CONTENT);
        }catch (UserException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }
}

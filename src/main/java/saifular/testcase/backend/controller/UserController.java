package saifular.testcase.backend.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saifular.testcase.backend.ExceptionHandler.EmailAlreadyExistsException;
import saifular.testcase.backend.ExceptionHandler.UserNotFoundException;
import saifular.testcase.backend.ExceptionHandler.UsernameAlreadyExistsException;
import saifular.testcase.backend.dto.UserDto;
import saifular.testcase.backend.dto.UserResponseDto;
import saifular.testcase.backend.entity.User;
import saifular.testcase.backend.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto){
        try {
            User newUser = userService.createUser(userDto);
            return ResponseEntity.ok(newUser);
        }
        catch (UsernameAlreadyExistsException exception){
            log.error("Username already exists : {}", userDto.getUsername());// Tambahkan log
            throw exception;
        }
        catch (EmailAlreadyExistsException exception){
            log.error("Email already exists : {}", userDto.getEmail());// Tambahkan log
            throw exception;
        }
        catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> fetchAllUser(){
        List<UserResponseDto> users = userService.getAllUser().stream()
                .map(user -> new UserResponseDto(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try {
            UserResponseDto user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        }catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @Valid @RequestBody UserDto userDto){
        try {
            userService.updateUser(id, userDto);
            return ResponseEntity.ok("Berhasil update data user");
        }catch (RuntimeException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User sukses dihapus");
        }
        catch (UserNotFoundException exception){
            log.error("User not found with id : " + id );
            throw exception;
        }
        catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}

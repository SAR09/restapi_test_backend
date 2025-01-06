package saifular.testcase.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import saifular.testcase.backend.ExceptionHandler.EmailAlreadyExistsException;
import saifular.testcase.backend.ExceptionHandler.UserNotFoundException;
import saifular.testcase.backend.ExceptionHandler.UsernameAlreadyExistsException;
import saifular.testcase.backend.dto.UserDto;
import saifular.testcase.backend.dto.UserResponseDto;
import saifular.testcase.backend.entity.User;
import saifular.testcase.backend.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(UserDto userDto){
        log.info("Debugging started...");
        if (userRepository.existsByUsername(userDto.getUsername())){
            throw new UsernameAlreadyExistsException("Username sudah terdaftar");
        }
        if (userRepository.existsByEmail(userDto.getEmail())){
            throw new EmailAlreadyExistsException("Email sudah terdaftar");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());

        return userRepository.save(user);
    }

    public List<User> getAllUser (){
        return userRepository.findAll();
    }

    public UserResponseDto getUserById(Long id){
       User user = userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("User with Id " + id +" not found"));

       return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail());

    }

    public UserDto updateUser(Long id, UserDto userDto){
         User user = userRepository.findById(id)
                 .orElseThrow(()->new RuntimeException("User not found"));

         user.setUsername(userDto.getUsername());
         user.setPassword(passwordEncoder.encode(userDto.getPassword()));
         user.setEmail(userDto.getEmail());

        userRepository.save(user);
        return new UserDto(user.getId(), user.getUsername(), null, user.getEmail());
    }

    public void deleteUser(Long id){
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        userRepository.deleteById(id);
    }

}

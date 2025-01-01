package saifular.testcase.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import saifular.testcase.backend.dto.UserDto;
import saifular.testcase.backend.dto.UserResponseDto;
import saifular.testcase.backend.entity.User;
import saifular.testcase.backend.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(UserDto userDto){
        if (userRepository.existsByUsername(userDto.getUsername())){
            throw new RuntimeException("Username sudah terdaftar");
        }
        if (userRepository.existsByEmail(userDto.getEmail())){
            throw new RuntimeException("Email sudah terdaftar");
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
        userRepository.deleteById(id);
    }

}

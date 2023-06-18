package com.odilosigningapp.service.implementation;

import com.odilosigningapp.Models.Role;
import com.odilosigningapp.Models.User;
import com.odilosigningapp.dto.UserDto;
import com.odilosigningapp.repository.RoleRepository;
import com.odilosigningapp.repository.UserRepository;
import com.odilosigningapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

   @Autowired
   UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

   @Autowired
   PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        setRolesForUser(user);
        userRepository.save(user);
    }

    private void setRolesForUser(User user) {
        instertRolesIfNotPresent();
        Role userRole = roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(userRole));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findAuthenticatedUser(Authentication authentication) {
        // Retrieve the authenticated user's information
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Create a User object or retrieve it from  database based on the user details
        User user = findByEmail(userDetails.getUsername());

        return user;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private void instertRolesIfNotPresent() {
        Role adminRoleInDb = roleRepository.findByName("ADMIN");
        Role userRoleInDb = roleRepository.findByName("USER");
        if (adminRoleInDb == null) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }
        if (userRoleInDb == null) {
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }
    }
}

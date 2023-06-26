package com.example.authenticationmicroservice.Service.Impl;

import com.example.authenticationmicroservice.Entity.Role;
import com.example.authenticationmicroservice.Entity.User;
import com.example.authenticationmicroservice.Repository.RoleRepository;
import com.example.authenticationmicroservice.Repository.UserRepository;
import com.example.authenticationmicroservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private PasswordEncoder passwordEncoder;


    @Override
    public User saveUser(User user) {
        log.info("saving user  : {} ", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public User getUserByUserName(String username) {

        log.info("saving user  : {} ", username);
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding role {}  to user {} ", roleName, username);
        this.userRepository.findByUsername(username)
                .ifPresent(user -> {
                    final Role role = roleRepository.findByName(roleName).orElse(null);
                    user.getRoles().add(role);
                });
    }

    @Override
    public List<User> getUsers() {
        log.info("find all users");
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = this.userRepository.findByUsername(username);
        if (!user.isPresent()) {
            log.info("user not found ");
            throw new UsernameNotFoundException("user not  found ");
        } else {
            List<SimpleGrantedAuthority> authoroties = new ArrayList<>();
            user.get().getRoles().forEach(role -> authoroties.add(new SimpleGrantedAuthority(role.getName())));
            return new org.springframework.security.core.userdetails
                    .User(user.get().getUsername(), user.get().getPassword(), authoroties);
        }

    }
}

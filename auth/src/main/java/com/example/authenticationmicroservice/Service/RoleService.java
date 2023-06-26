package com.example.authenticationmicroservice.Service;


import com.example.authenticationmicroservice.Entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {

    Role saveRole(Role role) ;

}

package com.example.authenticationmicroservice.Controller;

import com.example.authenticationmicroservice.Entity.User;
import com.example.authenticationmicroservice.Service.UserService;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
@Api("User API")
public class UserController {

    private final UserService userService;


    /**
     * get all users
     * @return users
     */

    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> findALl() {
        return new ResponseEntity<>(this.userService.getUsers(), HttpStatus.OK);
    }

    /**
     * save a new user
     * @return users
     */

    @PostMapping(value = "/user/save")
    public ResponseEntity<User> save(@RequestBody User user) {
        return new ResponseEntity<>(this.userService.saveUser(user), HttpStatus.CREATED);
    }

    /**
     * add a role to a specific user
     * @return void
     */
    @PutMapping(value="/user/add-role")
    public ResponseEntity<?> updateUserRoles( RoleRequest roleRequest){
        this.userService.addRoleToUser(roleRequest.userName , roleRequest.userName );
         return new ResponseEntity<>(null , HttpStatus.CREATED) ;
    }





    @Data

    static  class RoleRequest{
        private String userName ;
        private String roleName ;
    }



}

package com.psj.jwtExample.Controller;

import com.psj.jwtExample.Entities.User;
import com.psj.jwtExample.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getusers")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public List<User> getAllusers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getuser/{id}")
    @PreAuthorize("hasAuthority('USER_ROLES')")
    public User getUser(@PathVariable Integer id){
        return userService.getUser(id);
    }


}

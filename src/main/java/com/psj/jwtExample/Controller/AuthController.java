package com.psj.jwtExample.Controller;

import com.psj.jwtExample.Entities.AuthRequest;
import com.psj.jwtExample.Entities.AuthResponse;
import com.psj.jwtExample.Entities.User;
import com.psj.jwtExample.Services.JwtService;
import com.psj.jwtExample.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @PostMapping("/adduser")
    public String addUser(@RequestBody User user){
      return userService.addUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest authRequest){
        LinkedHashMap<String, ?> map = new LinkedHashMap<>();
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            String token = jwtService.generateToken(authRequest.getUsername());
            AuthResponse response = new AuthResponse(token);
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Invalid username or password"));
        }
    }

    


}

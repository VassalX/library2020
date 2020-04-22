package com.library2020.controller;

import com.library2020.model.User;
import com.library2020.payload.response.MessageResponse;
import com.library2020.repository.OrderRepository;
import com.library2020.repository.UserRepository;
import com.library2020.security.CurrentUser;
import com.library2020.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/me")
    public UserDetailsImpl getCurrentUser(@CurrentUser UserDetailsImpl currentUser) {
        return currentUser;
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getUserOrders(@PathVariable(value="id") Long id){
        Optional<User> userFound = userRepository.findById(id);
        if(!userFound.isPresent()){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(
                            String.format("Error: User with id %s doesn't exist",id)));
        }
        return ResponseEntity.ok(userFound.get().getOrders());
    }
}
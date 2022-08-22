package com.black.finance.rest.controller;

import com.black.finance.domain.entity.Usuario;
import com.black.finance.domain.repository.UserRepository;
import com.black.finance.exception.ResourceNotFoundException;
import com.black.finance.rest.dto.UserProfile;
import com.black.finance.security.CurrentUser;
import com.black.finance.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/me")
    public UserProfile getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    	UserProfile userSummary = new UserProfile(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/checkUsernameAvailability")
    public Boolean checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return isAvailable;
    }


    @GetMapping("/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        Usuario usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", username));

           UserProfile userProfile = new UserProfile(usuario.getId(), usuario.getUsername(), usuario.getName());

        return userProfile;
    }

}

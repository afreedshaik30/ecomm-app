package com.sb.main.controller;

import com.sb.main.dto.CustomerSignUpRequest;
import com.sb.main.dto.UpdatePasswordRequest;
import com.sb.main.model.User;
import com.sb.main.repository.UserRepository;
import com.sb.main.service.Interface.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bmart/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private User getCurrentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @PostMapping
    public ResponseEntity<User> registerCustomer(@Valid @RequestBody CustomerSignUpRequest customer) {
        User addedUser = userService.addUserCustomer(customer);
        return ResponseEntity.ok(addedUser);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/update-password")
    public ResponseEntity<User> updateOwnPassword(
            @Valid @RequestBody UpdatePasswordRequest newPasswordDTO,
            Authentication authentication) {

        User currentUser = getCurrentUser(authentication);
        User updatedUser = userService.changePassword(currentUser.getUserId(), newPasswordDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<User> getOwnDetails(Authentication authentication) {
        User currentUser = getCurrentUser(authentication);
        return ResponseEntity.ok(currentUser);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllCustomers() {
        List<User> users = userService.getAllUserDetails();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/deactivate")
    public ResponseEntity<String> deactivateOwnAccount(Authentication authentication) {
        User currentUser = getCurrentUser(authentication);
        logger.info("User deactivating their account: {}", currentUser.getEmail());

        String message = userService.deactivateUser(currentUser.getUserId());
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/reactivate")
    public ResponseEntity<String> reactivateOwnAccount(Authentication authentication) {
        User currentUser = getCurrentUser(authentication);
        String message = userService.reactivateUser(currentUser.getUserId());
        return ResponseEntity.ok(message);
    }
}

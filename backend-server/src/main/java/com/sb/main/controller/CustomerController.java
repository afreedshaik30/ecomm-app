package com.sb.main.controller;

import com.sb.main.dto.CustomerSignUpRequest;
import com.sb.main.dto.UpdatePasswordRequest;
import com.sb.main.model.User;
import com.sb.main.service.Impl.UserServiceImpl;
import com.sb.main.service.Interface.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bmart/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class); // Add logger

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody CustomerSignUpRequest customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        User addedUser = userService.addUserCustomer(customer);
        return ResponseEntity.ok(addedUser);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/update-password/{customerId}")
    public ResponseEntity<User> updateUserPassword(@PathVariable("customerId") Integer customerId,
                                                   @Valid @RequestBody UpdatePasswordRequest newPasswordDTO) {
        User updatedUser = userService.changePassword(customerId, newPasswordDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/deactivate/{customerId}")
    public ResponseEntity<String> deactivateUser(@PathVariable("customerId") Integer customerId) {
        logger.info("Inside the deactivate method");
        String message = userService.deactivateUser(customerId);
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{customerId}")
    public ResponseEntity<User> getUserDetails(@PathVariable("customerId") Integer customerId) {
        User user = userService.getUserDetails(customerId);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUserDetails() {
        List<User> users = userService.getAllUserDetails();
        return ResponseEntity.ok(users);
    }

}
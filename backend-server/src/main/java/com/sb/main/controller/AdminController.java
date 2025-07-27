package com.sb.main.controller;

import com.sb.main.dto.AdminSignUpRequest;
import com.sb.main.dto.UpdatePasswordRequest;
import com.sb.main.model.User;
import com.sb.main.service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bmart/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody AdminSignUpRequest admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        User addedUser = userService.addUserAdmin(admin);
        return ResponseEntity.ok(addedUser);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update-password/{adminId}")
    public ResponseEntity<User> updateUserPassword(@PathVariable("adminId") Integer customerId, @RequestBody UpdatePasswordRequest newPasswordDTO) {
        User updatedUser = userService.changePassword(customerId, newPasswordDTO);
        return ResponseEntity.ok(updatedUser);
    }
}

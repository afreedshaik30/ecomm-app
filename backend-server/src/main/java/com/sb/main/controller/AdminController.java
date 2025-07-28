package com.sb.main.controller;

import com.sb.main.dto.AdminSignUpRequest;
import com.sb.main.dto.UpdatePasswordRequest;
import com.sb.main.enums.UserRole;
import com.sb.main.exception.UserException;
import com.sb.main.model.User;
import com.sb.main.service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bmart/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody AdminSignUpRequest admin, @RequestParam String secretKey) {
        if (!secretKey.equals("208T1A0560")) {
            throw new UserException("Unauthorized access");
        }
        User addedUser = userService.addUserAdmin(admin);
        return ResponseEntity.ok(addedUser);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update-password/{adminId}")
    public ResponseEntity<User> updateAdminPassword(@PathVariable Integer adminId, @RequestBody UpdatePasswordRequest dto) {
        User target = userService.getUserDetails(adminId);
        if (!target.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new AccessDeniedException("Cannot update password for non-admin users.");
        }
        User updated = userService.changePassword(adminId, dto);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deactivate/{userId}")
    public ResponseEntity<String> deactivateUser(@PathVariable Integer userId) {
        User user = userService.getUserDetails(userId);
        if (user.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new AccessDeniedException("You cannot deactivate another admin.");
        }
        return ResponseEntity.ok(userService.deactivateUser(userId));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/reactivate/{userId}")
    public ResponseEntity<String> reactivateUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.reactivateUser(userId));
    }
}
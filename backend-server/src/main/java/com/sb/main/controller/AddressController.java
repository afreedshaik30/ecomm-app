package com.sb.main.controller;

import com.sb.main.model.Address;
import com.sb.main.model.User;
import com.sb.main.repository.UserRepository;
import com.sb.main.service.Interface.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bmart/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Address> addAddressToUser(@Valid @RequestBody Address address) {
        User currentUser = getCurrentUser();
        Address addedAddress = addressService.addAddressToUser(currentUser.getUserId(), address);
        return new ResponseEntity<>(addedAddress, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Address>> getAllUserAddress() {
        User currentUser = getCurrentUser();
        List<Address> allUserAddress = addressService.getAllUserAddress(currentUser.getUserId());
        return ResponseEntity.ok(allUserAddress);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateAddress(
            @Valid @RequestBody Address address,
            @PathVariable Integer addressId
    ) {
        User currentUser = getCurrentUser();
        addressService.validateAddressOwnership(addressId, currentUser); // 🔒 important
        Address updatedAddress = addressService.updateAddress(address, addressId);
        return ResponseEntity.ok(updatedAddress);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> removeAddress(@PathVariable Integer addressId) {
        User currentUser = getCurrentUser();
        addressService.validateAddressOwnership(addressId, currentUser); // 🔒 important
        addressService.removeAddress(addressId);
        return ResponseEntity.ok("Address removed successfully");
    }
}

package com.sb.main.controller;

import com.sb.main.model.Address;
import com.sb.main.service.Interface.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bmart/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/{userId}")
    public ResponseEntity<Address> addAddressToUser(
            @PathVariable Integer userId, @Valid @RequestBody Address address) {
        Address addedAddress = addressService.addAddressToUser(userId, address);
        return new ResponseEntity<>(addedAddress, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<List<Address>> getAllUserAddress(@PathVariable Integer userId) {
        List<Address> allUserAddress = addressService.getAllUserAddress(userId);
        return ResponseEntity.ok(allUserAddress);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateAddress(@Valid @RequestBody Address address, @PathVariable Integer addressId) {
        Address updatedAddress = addressService.updateAddress(address,addressId);
        return ResponseEntity.ok(updatedAddress);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> removeAddress(@PathVariable Integer addressId) {
        addressService.removeAddress(addressId);
        return ResponseEntity.ok("Address removed successfully");
    }
}

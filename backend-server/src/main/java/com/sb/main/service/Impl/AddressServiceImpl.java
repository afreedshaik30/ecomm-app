package com.sb.main.service.Impl;

import com.sb.main.enums.UserRole;
import com.sb.main.exception.AddressException;
import com.sb.main.exception.UserException;
import com.sb.main.model.Address;
import com.sb.main.model.User;
import com.sb.main.repository.AddressRepository;
import com.sb.main.repository.UserRepository;
import com.sb.main.service.Interface.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Address addAddressToUser(Integer userId, Address address) throws AddressException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        address.setUser(user);
        Address savedAddress = addressRepository.save(address);

        user.getAddress().add(savedAddress);
        userRepository.save(user);

        return savedAddress;
    }

    @Override
    public Address updateAddress(Address address, Integer addressId) throws AddressException {
        Address existing = getAddressById(addressId);

        existing.setDoorNum(address.getDoorNum());
        existing.setStreet(address.getStreet());
        existing.setArea(address.getArea());
        existing.setCity(address.getCity());
        existing.setPinCode(address.getPinCode());
        existing.setState(address.getState());

        return addressRepository.save(existing);
    }

    @Override
    public void removeAddress(Integer addressId) throws AddressException {
        Address address = getAddressById(addressId);
        addressRepository.delete(address);
    }

    @Override
    public List<Address> getAllUserAddress(Integer userId) throws AddressException {
        List<Address> addresses = addressRepository.getUserAddressList(userId);
        if (addresses.isEmpty()) {
            throw new AddressException("User has no addresses.");
        }
        return addresses;
    }

    @Override
    public void validateAddressOwnership(Integer addressId, User currentUser) {
        Address address = getAddressById(addressId);
        if (!address.getUser().getUserId().equals(currentUser.getUserId()) &&
                !currentUser.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new AccessDeniedException("Unauthorized access to address");
        }
    }

    private Address getAddressById(Integer addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressException("Address not found"));
    }
}
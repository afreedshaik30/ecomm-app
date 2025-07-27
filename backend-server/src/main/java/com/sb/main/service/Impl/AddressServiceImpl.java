package com.sb.main.service.Impl;

import com.sb.main.exception.AddressException;
import com.sb.main.model.Address;
import com.sb.main.model.User;
import com.sb.main.repository.AddressRepository;
import com.sb.main.repository.UserRepository;
import com.sb.main.service.Interface.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Address addAddressToUser(Integer userId, Address address) throws AddressException {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Fouund"));

        Address saveaAddress = addressRepository.save(address);
        saveaAddress.setUser(existingUser);

        existingUser.getAddress().add(saveaAddress);
        userRepository.save(existingUser);
        return saveaAddress;
    }

    @Override
    public Address updateAddress(Address address, Integer addressId) throws AddressException {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressException("Address not found"));

        existingAddress.setDoorNum(address.getDoorNum());
        existingAddress.setStreet(address.getStreet());
        existingAddress.setArea(address.getArea());
        existingAddress.setCity(address.getCity());
        existingAddress.setPinCode(address.getPinCode());
        existingAddress.setState(address.getState());

        return addressRepository.save(existingAddress);
    }

    @Override
    public void removeAddress(Integer addressId) throws AddressException {
        // get address by ID and delete
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressException("Address not found"));
        addressRepository.delete(existingAddress);
    }

    @Override
    public List<Address> getAllUserAddress(Integer userId) throws AddressException {
        List<Address> userAddressList = addressRepository.getUserAddressList(userId);
        if (userAddressList.isEmpty()) {
            System.out.println("empty");
            throw new AddressException("User does not have any address");
        }
        return userAddressList;
    }
}

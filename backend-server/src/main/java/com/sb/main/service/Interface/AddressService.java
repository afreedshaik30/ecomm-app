package com.sb.main.service.Interface;

import com.sb.main.exception.AddressException;
import com.sb.main.model.Address;
import com.sb.main.model.User;

import java.util.List;

public interface AddressService {

     Address addAddressToUser(Integer userId, Address address);

     List<Address> getAllUserAddress(Integer userId);

     Address updateAddress(Address address, Integer addressId);

     void removeAddress(Integer addressId);

     void validateAddressOwnership(Integer addressId, User currentUser);
}


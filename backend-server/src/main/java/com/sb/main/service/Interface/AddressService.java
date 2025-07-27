package com.sb.main.service.Interface;

import com.sb.main.exception.AddressException;
import com.sb.main.model.Address;

import java.util.List;

public interface AddressService {
     Address addAddressToUser(Integer userId,Address address) throws AddressException;
     Address updateAddress(Address address, Integer addressId) throws AddressException ;
     void removeAddress(Integer addressId)throws AddressException;
     List<Address> getAllUserAddress(Integer userId)throws AddressException;
}

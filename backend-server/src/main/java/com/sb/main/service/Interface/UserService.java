package com.sb.main.service.Interface;

import com.sb.main.dto.AdminSignUpRequest;
import com.sb.main.dto.CustomerSignUpRequest;
import com.sb.main.dto.UpdatePasswordRequest;
import com.sb.main.exception.UserException;
import com.sb.main.model.User;

import java.util.List;

public interface UserService {
    User getUserByEmailId(String emailId)throws UserException;
    User addUserCustomer(CustomerSignUpRequest customer)  throws UserException;
    User addUserAdmin(AdminSignUpRequest admin)  throws UserException;
    User changePassword(Integer userId, UpdatePasswordRequest customer)  throws UserException;
    User getUserDetails(Integer userId)throws UserException;
    List<User> getAllUserDetails() throws UserException;
    String deactivateUser(Integer userId) throws UserException;
    String reactivateUser(Integer userId);
}

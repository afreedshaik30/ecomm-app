package com.sb.main.service.Impl;

import com.sb.main.repository.UserRepository;
import com.sb.main.dto.AdminSignUpRequest;
import com.sb.main.dto.CustomerSignUpRequest;
import com.sb.main.dto.UpdatePasswordRequest;
import com.sb.main.enums.UserAccountStatus;
import com.sb.main.enums.UserRole;
import com.sb.main.exception.UserException;
import com.sb.main.model.User;
import com.sb.main.service.Interface.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//  private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class); // Add logger

    @Override
    public User getUserByEmailId(String emailId) throws UserException {
        return userRepository.findByEmail(emailId).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public User addUserCustomer(CustomerSignUpRequest customer) throws UserException {
        if (customer == null)
            throw new UserException("Customer Can't be Null");
        Optional<User> findByEmail = userRepository.findByEmail(customer.getEmail());
        if (findByEmail.isPresent()) {
            logger.info("Inside add user method");
            throw new RuntimeException("Email already Register");
        }

        User newCustomer = new User();
        newCustomer.setFullName(customer.getFullName());
        newCustomer.setPhoneNumber(customer.getPhoneNumber());
        newCustomer.setEmail(customer.getEmail());
//        newCustomer.setPassword(customer.getPassword());
//        newCustomer.setPassword(passwordEncoder.encode(customer.getPassword()));
        newCustomer.setRole(UserRole.ROLE_USER);
        newCustomer.setRegisteredAt(LocalDateTime.now());
        newCustomer.setUserAccountStatus(UserAccountStatus.ACTIVE);
        return userRepository.save(newCustomer);
    }

    @Override
    public User addUserAdmin(AdminSignUpRequest admin) throws UserException {
        if (admin == null)
            throw new UserException("Admin Can't be Null");
        Optional<User> findByEmail = userRepository.findByEmail(admin.getEmail());
        if (findByEmail.isPresent()) {
            logger.info("Inside add user method");
            throw new RuntimeException("Email Already Registered");
        }
        User newAdmin = new User();
        newAdmin.setFullName(admin.getFullName());
        newAdmin.setPhoneNumber(admin.getPhoneNumber());
        newAdmin.setEmail(admin.getEmail());
//        newAdmin.setPassword(admin.getPassword());
//        newAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
        newAdmin.setRole(UserRole.ROLE_ADMIN);
        newAdmin.setRegisteredAt(LocalDateTime.now());
        newAdmin.setUserAccountStatus(UserAccountStatus.ACTIVE);
        return userRepository.save(newAdmin);
    }

    @Override
    public User changePassword(Integer userId, UpdatePasswordRequest customer) throws UserException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
        if (customer.getNewPassword().length() >= 5 && customer.getNewPassword().length() <= 10) {
//            user.updatePassword(customer.getNewPassword(), passwordEncoder);
            return userRepository.save(user);
        } else {
            throw new UserException("provide valid  password");
        }
    }

    @Override
    public String deactivateUser(Integer userId) throws UserException {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
        existingUser.setUserAccountStatus(UserAccountStatus.DEACTIVATED);
        userRepository.save(existingUser);
        return "Account deactivet Succesfully";
    }

    @Override
    public User getUserDetails(Integer userId) throws UserException {
        return userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public List<User> getAllUserDetails() throws UserException {
        List<User> existingAllUser = userRepository.findAll();
        if (existingAllUser.isEmpty()) {
            throw new UserException("User list is Empty");
        }
        return existingAllUser;
    }
}

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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Get user by email ID
     */
    @Override
    public User getUserByEmailId(String emailId) {
        return userRepository.findByEmail(emailId)
                .orElseThrow(() -> new UserException("User not found"));
    }

    /**
     * Register a new customer
     */
    @Override
    public User addUserCustomer(CustomerSignUpRequest customer) {
        if (customer == null) throw new UserException("Customer request cannot be null");

        userRepository.findByEmail(customer.getEmail()).ifPresent(existing -> {
            logger.info("Email already registered for customer: {}", existing.getEmail());
            throw new RuntimeException("Email already registered");
        });

        User newCustomer = new User();
        newCustomer.setFullName(customer.getFullName());
        newCustomer.setPhoneNumber(customer.getPhoneNumber());
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setPassword(passwordEncoder.encode(customer.getPassword()));
        newCustomer.setRole(UserRole.ROLE_USER);
        newCustomer.setRegisteredAt(LocalDateTime.now());
        newCustomer.setUserAccountStatus(UserAccountStatus.ACTIVE);

        return userRepository.save(newCustomer);
    }

    /**
     * Register a new admin
     */
    @Override
    public User addUserAdmin(AdminSignUpRequest admin) {
        if (admin == null) throw new UserException("Admin request cannot be null");

        userRepository.findByEmail(admin.getEmail()).ifPresent(existing -> {
            logger.info("Email already registered for admin: {}", existing.getEmail());
            throw new RuntimeException("Email already registered");
        });

        User newAdmin = new User();
        newAdmin.setFullName(admin.getFullName());
        newAdmin.setPhoneNumber(admin.getPhoneNumber());
        newAdmin.setEmail(admin.getEmail());
        newAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
        newAdmin.setRole(UserRole.ROLE_ADMIN);
        newAdmin.setRegisteredAt(LocalDateTime.now());
        newAdmin.setUserAccountStatus(UserAccountStatus.ACTIVE);

        return userRepository.save(newAdmin);
    }

    /**
     * Update password for the given user
     */
    @Override
    public User changePassword(Integer userId, UpdatePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        String newPassword = request.getNewPassword();
        if (newPassword.length() < 5 || newPassword.length() > 10) {
            throw new UserException("Password length must be between 5 and 10 characters");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    /**
     * Get details of a single user
     */
    @Override
    public User getUserDetails(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));
    }

    /**
     * Get all users
     */
    @Override
    public List<User> getAllUserDetails() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserException("User list is empty");
        }
        return users;
    }

    /**
     * Deactivate user account
     */
    @Override
    public String deactivateUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        user.setUserAccountStatus(UserAccountStatus.DEACTIVATED);
        userRepository.save(user);

        return "Account deactivated successfully";
    }

    /**
     * Reactivate user account
     */
    @Override
    public String reactivateUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        if (user.getUserAccountStatus() == UserAccountStatus.ACTIVE) {
            return "User is already active";
        }

        user.setUserAccountStatus(UserAccountStatus.ACTIVE);
        userRepository.save(user);

        return "Account reactivated successfully";
    }
}

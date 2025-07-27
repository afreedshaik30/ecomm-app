package com.sb.main.controller;

import com.sb.main.dto.LoginResponse;
import com.sb.main.exception.UserException;
import com.sb.main.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bmart")
public class LoggedInController {

    @Autowired
    private UserService userService;

    @PreAuthorize("isAuthenticated()") // Open for all authenticated users
    @GetMapping("/me")
    public ResponseEntity<LoginResponse> getLoggedInCustomerDetailsHandler(Authentication auth) {
        try {var customer = userService.getUserByEmailId(auth.getName());
            LoginResponse loginData = new LoginResponse();
            loginData.setId(customer.getUserId());
            loginData.setFullName(customer.getFullName());
            loginData.setLoginStatus("Success");

            return new ResponseEntity<>(loginData, HttpStatus.OK);}
        catch(UserException ex ){
            throw new UserException(" Invalid Password");
        }

    }
}

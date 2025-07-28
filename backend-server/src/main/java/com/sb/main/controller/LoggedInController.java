//package com.sb.main.controller;
//
//import com.sb.main.dto.LoginResponse;
//import com.sb.main.model.User;
//import com.sb.main.service.Interface.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/bmart")
//@RequiredArgsConstructor
//public class LoggedInController {
//
//    private final UserService userService;
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/me")
//    public ResponseEntity<LoginResponse> getLoggedInUserDetails(Authentication authentication) {
//        String email = authentication.getName();
//
//        User user = userService.getUserByEmailId(email);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with email: " + email);
//        }
//
//        LoginResponse response = new LoginResponse();
//        response.setId(user.getUserId());
//        response.setFullName(user.getFullName());
//        response.setLoginStatus("Success");
//
//        return ResponseEntity.ok(response);
//    }
//}

package com.sb.main.security;

import com.sb.main.enums.UserAccountStatus;
import com.sb.main.model.User;
import com.sb.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        System.out.println("🔍 Loading user by email: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        System.out.println("User found");
        if (user.getUserAccountStatus() == UserAccountStatus.DEACTIVATED) {
            throw new DisabledException("Account is deactivated. Please contact admin.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name())) // "ROLE_USER", "ROLE_ADMIN"
        );
    }
}

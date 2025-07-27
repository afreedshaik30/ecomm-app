package com.sb.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sb.main.enums.UserAccountStatus;
import com.sb.main.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String fullName;
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime registeredAt;

    @Enumerated(EnumType.STRING)
    private UserAccountStatus userAccountStatus;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    private Cart cart;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();;

//    @JsonIgnore
//    @OneToMany(mappedBy = "user")
//    private List<Review> reviews = new ArrayList<>();;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

//    public void updatePassword(String newPassword, PasswordEncoder passwordEncoder) {
//        String hashedPassword = passwordEncoder.encode(newPassword);
//        this.setPassword(hashedPassword);
//    }
}

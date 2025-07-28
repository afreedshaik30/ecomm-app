package com.sb.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @NotBlank(message = "Door Num is Mandatory")
    @Column(name = "door_num")
    private String doorNum;

    @NotBlank(message = "Street Name Mandatory")
    private String street;

    @NotBlank(message = "Area Name Mandatory")
    private String area;

    @Size(max = 10)
    @NotBlank(message = "City Name Mandatory")
    private String city;

    @Size(min = 6, max = 6, message = "PinCode must be 6 digits")
    @NotBlank(message = "PinCode is Mandatory")
    @Column(name = "pin_code")
    private String pinCode;

    @Size(max = 20)
    @NotBlank(message = "State Name Mandatory")
    private String state;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
}

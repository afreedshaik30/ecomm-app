package com.sb.main.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerSignUpRequest {

    @NotBlank(message = "Full Name can not be blank")
    private String fullName;

    @NotBlank(message = "Phone Number can not be blank")
    @Size(max = 10,min = 10,message = "Phone Number should be minumum 10 digit max 12")
    private String phoneNumber;

    @NotBlank(message = "Email Id Is Mandatory")
    private String email;

    @NotBlank(message = "Password Is Mandatory")
    @Size(max = 15,min = 5,message = "Password length should be more than 5 Character")
    private String password;
}

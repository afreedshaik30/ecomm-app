package com.sb.main.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
    @NotBlank(message = "New password can not be Blank..")
    @Size(min = 5,max = 10)
    private String newPassword;
}

package com.sb.main.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginResponse {
    Integer id;
    String fullName;
    String loginStatus;
}

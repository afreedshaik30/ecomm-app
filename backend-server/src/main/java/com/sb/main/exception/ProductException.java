package com.sb.main.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductException extends RuntimeException {
    public ProductException(String message) {
        super(message);
    }
}

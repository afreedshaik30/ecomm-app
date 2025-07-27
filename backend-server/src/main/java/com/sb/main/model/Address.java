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
    private Integer addressID;

    @NotBlank(message = "Door Num is Mandatory")
    private String doorNum;

    @NotBlank(message = "Street Name Mandatory")
    private String street;

    @NotBlank(message = "Area Name Mandatory")
    private String area;

    @Size(max = 10)
    @NotBlank(message = "City Name Mandatory")
    private String city;

    @NotBlank(message = "PinCode is Mandatory")
    private String PinCode;

    @Size(max = 20)
    @NotBlank(message = "State Name Mandatory")
    private String state;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userid")
    private User user;
}

package com.example.demo2.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Setter
public class CustomerRegistrationDTO {
    @NotBlank @NotNull
    private String firstName;
    @NotBlank @NotNull
    private String lastName;
    @NotBlank @NotNull
    private String password;
    @NotBlank @NotNull @Email
    private String email;
    @Size(min=18, max=110, message="Invalid age")
    private int age;
    private String city;
}

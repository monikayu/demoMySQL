package com.example.demo2.model.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor //когато се пълни от базата данни => All args constructor
@Setter             //когато се запълват полета от json => No args constructor
@Getter
public class MaxAgeByRegDateDTO {
    private int maxAge;
    private LocalDate registrationDate;
    private long count;
}

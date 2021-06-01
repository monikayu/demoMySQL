package com.example.demo2.model.custom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DateParameterDTO {
    private LocalDate date;
}

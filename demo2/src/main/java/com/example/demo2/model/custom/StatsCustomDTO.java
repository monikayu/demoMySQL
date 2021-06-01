package com.example.demo2.model.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatsCustomDTO {
    private String city;
    private Integer minAge;
    private Integer maxAge;
    private Double averageAge;
    private long sum;

    public StatsCustomDTO(String city, Integer minAge, Integer maxAge, Double averageAge, long sum) {
        this.city = city;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.averageAge = averageAge;
        this.sum = sum;
    }
}

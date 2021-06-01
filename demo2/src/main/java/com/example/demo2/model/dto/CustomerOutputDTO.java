package com.example.demo2.model.dto;

import com.example.demo2.model.pojo.Customer;
import com.example.demo2.model.pojo.Role;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class CustomerOutputDTO {
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private Role role;
    //private Customer.Address address;

    @Column(name="registration_date")
    @Temporal(TemporalType.DATE)
    private Date registrationDate; //temporal cannot be used with LocalDate


    public CustomerOutputDTO(Customer cstm) {
        this.firstName = cstm.getFirstName();
        this.lastName = cstm.getLastName();
        this.email = cstm.getEmail();
        //this.address = cstm.getAddress();
        this.age = cstm.getAge();
    }
}

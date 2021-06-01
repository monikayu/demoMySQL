package com.example.demo2.model.pojo;

import com.example.demo2.model.dto.CustomerRegistrationDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customers", schema = "bank")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", nullable = false)
    private Long id;
    @Column(name="first_name", length = 45) @NotNull
    private String firstName;
    @Column(name="last_name", length = 45) @NotNull
    private String lastName;
    @Column(length = 45)
    @NotNull
    private String password;
    @Column(name="email_address", nullable = false, unique = true, length = 100)
    @Email @NotNull
    private String email;
    @NotNull @Size(min=18, max=110)
    private int age;
    @Column(name="active", nullable = false)
    private boolean isActive;
    @Enumerated
    private Role accountRole;
    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "customers_have_products",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    Set<Product> bankProducts;
    @Column(name="registration_date", nullable = false, updatable = false)
    private LocalDate registrationDate;
    public LocalDate getRegistrationDate(){
        return registrationDate;
    }

    public Customer(@Valid CustomerRegistrationDTO dto) {
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.age = dto.getAge();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.registrationDate = LocalDate.now();
        accountRole = Role.USER;
        isActive = true;
    }
    /*@NoArgsConstructor
    @Embeddable
    @Access(AccessType.PROPERTY)
    @Data
    @AllArgsConstructor
    private class Address {*/
        private String city;
        private String nationality;
        private String street;

}

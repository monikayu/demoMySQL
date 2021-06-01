package com.example.demo2.controller;

import com.example.demo2.model.custom.AverageAgeGrouped;
import com.example.demo2.model.custom.DateParameterDTO;
import com.example.demo2.model.custom.MaxAgeByRegDateDTO;
import com.example.demo2.model.custom.StatsCustomDTO;
import com.example.demo2.model.dto.CustomerRegistrationDTO;
import com.example.demo2.model.pojo.Customer;
import com.example.demo2.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
public class CustomerController {
    private static final int CONST_RESULTS_PER_PAGE = 2;
    private static final String CONST_SORTING_VARIALBE = "age";
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/users")
    public Customer registerUser(@RequestBody CustomerRegistrationDTO dto){
        Customer customer = new Customer(dto);
        return customerRepository.save(customer);
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/users/{id}")
    public Customer getById(@PathVariable Long id){
        return customerRepository.getById(id);
    }

    @GetMapping("/users/countRegistrationsSince")
    public String countRegistrationsSince(@RequestBody DateParameterDTO dto){
        LocalDate date = dto.getDate();
        return "Total registrations since " + date + " -> " + customerRepository.countUsers(date);
    }

    @GetMapping("/users/avgAge/groupedBy/lastName")
    public List<AverageAgeGrouped> getAverageAgeGroupedByLastName(){
        return customerRepository.findAverageAgeGroupedByLastName();
    }

    @GetMapping("/users/countByDate")
    public String getCountNewUsersAddedByDate(@RequestBody @Valid DateParameterDTO dto){
        LocalDate date = dto.getDate();
        return "Query 4: The count of new users added on this date was " +
                customerRepository.countUsers4(date);
    }

    @GetMapping("/users/maxAgeGrouped")
    public List<MaxAgeByRegDateDTO> getMaxAgeGrouped(@RequestBody DateParameterDTO dto){
        return customerRepository.getMaxAgeByRegistrationDate(dto.getDate());
    }

    @GetMapping("/users/findAllRegisteredAfterCertainDate")
    public List<Customer> findAllRegisteredAfterCertainDate(@RequestBody DateParameterDTO dto, @RequestParam("page") int page){
        //pagination done in Service class
        PageRequest request = PageRequest.of(page, CONST_RESULTS_PER_PAGE, Sort.by(CONST_SORTING_VARIALBE).ascending());
        Page<Customer> pageOutput = customerRepository.findAllRegisteredAfter2(dto.getDate(), request);
        return pageOutput.getContent();
    }

    @GetMapping("/users/search/")
    public List<Customer> searchByFirstAndLastName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("page") int page){
        //pagination done in Service class
        PageRequest request = PageRequest.of(page, CONST_RESULTS_PER_PAGE, Sort.by(CONST_SORTING_VARIALBE));
        return customerRepository.findCustomersByFirstAndLastName(firstName, lastName, request);
        //Кога трябва да връщам Page.getContent() от repo-то ?
    }

    @GetMapping("/users/countNewUsersBefore")
    public String findCountNewUsersBefore(@RequestBody DateParameterDTO dto){
        int count = customerRepository.countUsersBeforeDate(dto.getDate());
        return "Amount of new customer registrations before " + dto.getDate() + " -> " + count;
    }

    @PostMapping("/users/registration2")
    public int registerUser2(@RequestBody CustomerRegistrationDTO dto){
        return customerRepository.insertCustomerUsingQuery(dto.getEmail(),
                dto.getAge(), dto.getFirstName(), dto.getLastName(), dto.getPassword(), dto.getCity());
    }

    @GetMapping("/users/stats")
    public List<StatsCustomDTO> getStats(){
        return customerRepository.getStats();
    }

    @GetMapping("/users/all")
    public List<Customer> getAllSorted(){
        return customerRepository.findAll(Sort.by("firstName", "age"));
    }

    @GetMapping("/users/allPagination")
    public List<Customer> getAllPaginated(@RequestParam("page") int page, @RequestParam("size") int size){
        //pagination done in Service class
        PageRequest request = PageRequest.of(page, size);
        Page<Customer> pageOutput = customerRepository.findAll(request);
        return pageOutput.getContent();
    }


    @GetMapping("/users/allGreater/{id}")
    public List<Customer> getAll(@PathVariable long id, @RequestParam("page") int page, @RequestParam("size") int size){
        //pagination done in Service class
        if(page < 0 || size < 1){
            pageNotFound();
            return null;
        }
        PageRequest request = PageRequest.of(page, size, Sort.by(CONST_SORTING_VARIALBE).descending());
        List<Customer> list = customerRepository.getAllByIdGreaterThan(id, request);
        if(list.size() < 1){
            pageNotFound();
            return null;
        }
        return list;
    }

    private void pageNotFound(){
        System.out.println("Page not found");
    }
}

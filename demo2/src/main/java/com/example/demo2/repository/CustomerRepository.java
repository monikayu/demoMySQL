package com.example.demo2.repository;

import com.example.demo2.model.custom.AverageAgeGrouped;
import com.example.demo2.model.custom.MaxAgeByRegDateDTO;
import com.example.demo2.model.custom.StatsCustomDTO;
import com.example.demo2.model.pojo.Customer;
import com.example.demo2.model.pojo.Role;
import javafx.scene.control.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer save(Customer customer);

    public default Customer getById(Long id){
        Optional<Customer> c = findById(id);
        if(c.isPresent()){
            return c.get();
        }
        return null;
    }
    @Query("SELECT c FROM Customer AS c WHERE c.id = :id")
    public Optional<Customer> findById(@Param("id") Long id);

    @Query(value = "SELECT COUNT(first_name) AS count FROM customers WHERE registration_date > :date", nativeQuery = true)
    public Integer countUsers(@Param("date") LocalDate date);

    @Query("SELECT new com.example.demo2.model.custom.AverageAgeGrouped(AVG(c.age), c.lastName)  FROM Customer AS c GROUP BY c.lastName")
    public List<AverageAgeGrouped> findAverageAgeGroupedByLastName();

    @Query("SELECT new com.example.demo2.model.custom.StatsCustomDTO(c.city, MIN(c.age), MAX(c.age), AVG(c.age), SUM(c.age)) " +
            "FROM Customer AS c " +
            "GROUP BY c.city " +
            "ORDER BY c.city DESC")
    public List<StatsCustomDTO> getStats();

    @Query("FROM Customer WHERE id > :id")
    public List<Customer> getAllByIdGreaterThan(@Param("id") long id, Pageable pageable);

    @Query("SELECT COUNT(c.firstName) AS count FROM Customer c WHERE c.registrationDate = :regDate")
    public Integer countUsers4(@Param("regDate") LocalDate date); //date has to be wrapped into dto, otherwise it throws exception

    @Query("SELECT new com.example.demo2.model.custom.MaxAgeByRegDateDTO(MAX(c.age), c.registrationDate, COUNT(c)) " +
            "FROM Customer AS c GROUP BY c.registrationDate HAVING c.registrationDate > :minDate")
    public List<MaxAgeByRegDateDTO> getMaxAgeByRegistrationDate(@Param("minDate") LocalDate afterDate);

    @Query("SELECT c FROM Customer c WHERE c.registrationDate > ?1 ORDER BY c.firstName")
    public List<Customer> findAllRegisteredAfter(LocalDate date);

    @Query("SELECT c FROM Customer c WHERE c.registrationDate > :regDate ORDER BY c.age")
    public Page<Customer> findAllRegisteredAfter2(@Param("regDate") LocalDate registrationDate, Pageable pageable);


    @Query("SELECT c FROM Customer AS c WHERE " +
            "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :fiName, '%')) OR " +
            "LOWER(c.lastName) LIKE LOWER(CONCAT('%',:laName, '%'))")  //searching in the name or description
    List<Customer> findCustomersByFirstAndLastName(@Param("fiName") String firstName, @Param("laName") String lastName, Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="insert into bank.customers (first_name, last_name, age, email_address, password, registration_date, account_role, city) " +
            "values (:firstName,:lastName,:age, :emailAddress, " +
            ":password, curdate(), '1', :city)", nativeQuery = true)
    public int insertCustomerUsingQuery(@Param("emailAddress") String email,
                                        @Param("age") int age,
                                        @Param("firstName") String firstName, @Param("lastName") String lastName,
                                        @Param("password") String password, @Param("city") String city);

    @Query("SELECT COUNT(c.firstName) AS count FROM Customer AS c WHERE c.registrationDate < ?1")
    public Integer countUsersBeforeDate(LocalDate date);


    public Page findAll(Pageable request);

}
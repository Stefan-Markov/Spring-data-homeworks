package com.example.demo.repositories;

import com.example.demo.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByNameAndBirthDate(String name, LocalDateTime birthDate);

    //Query_1 Get all customers ordered by their birthdate in ascending order.
    // If two customers are born on the same date, first print those, who
    // are not young drivers (e.g. print experienced drivers first)
    @Query(value = "SELECT DISTINCT c FROM Customer AS c " +
            "ORDER BY c.birthDate, c.youngDriver ")
    List<Customer> findAllBirthDateAndIsYoungDriver();

    //Query_5 – Total Sales by Customer
    //Get all customers that have bought at least 1 car and get their names,
    // count of cars bought and total money spent on cars. Order the result
    // by total money spent in
    // descending order and then by total amount of cars bought again in descending order

    @Query(value = "SELECT  t.fullName as fullName,\n" +
            "       boughtCars as boughtCars,\n" +
            "       sum(p.price) as spentMoney\n" +
            "FROM (\n" +
            "         SELECT c.id,c.name as fullName, count(distinct s.car_id) as boughtCars\n" +
            "         FROM customers as c\n" +
            "         INNER JOIN sales s on c.id = s.customer_id\n" +
            "         GROUP BY s.customer_id\n" +
            "         ) as t\n" +
            "inner join cars c2 on t.id = c2.id\n" +
            "inner join parts_cars pc on c2.id = pc.car_id\n" +
            "inner join parts p on pc.part_id = p.id\n" +
            "GROUP BY c2.id " +
            "ORDER BY spentMoney DESC, boughtCars DESC ",
            nativeQuery = true)
    List<Object[]> findAllBySales();
}

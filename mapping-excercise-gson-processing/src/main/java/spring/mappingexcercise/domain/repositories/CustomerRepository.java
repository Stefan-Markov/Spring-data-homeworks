package spring.mappingexcercise.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.mappingexcercise.domain.entities.Customer;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Set<Customer> getAllByOrderByYoungDriverAscBirthDateAsc();

}

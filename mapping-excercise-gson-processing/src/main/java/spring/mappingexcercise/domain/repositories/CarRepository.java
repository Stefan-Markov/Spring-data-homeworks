package spring.mappingexcercise.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.mappingexcercise.domain.entities.Car;

import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Set<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);
}

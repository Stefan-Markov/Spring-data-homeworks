package com.example.demo.repositories;

import com.example.demo.models.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findByMakeAndModelAndTravelledDistance(String make
            , String model,Integer travelledDistance);

    //Query_2 Get all cars from make Toyota and order them by model
    // alphabetically and by travelled distance in descending order.
    List<Car> findAllByMakeEqualsOrderByModelAscTravelledDistanceDesc(String make);

    //Query_4 – Cars with Their List of Parts
    //Get all cars along with their list of parts. For the car get only make, model
    //and travelled distance and for the parts get only name and price.
    List<Car> findAll();
}

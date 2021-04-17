package com.example.demo.services;

import com.example.demo.models.dtos.CarSeedDto;
import com.example.demo.models.dtos.views.CarToyotaViewRootDto;
import com.example.demo.models.dtos.views.query4.CarWithPartsViewRootDto;
import com.example.demo.models.entities.Car;

import java.util.List;

public interface CarService {
    void seedCars(List<CarSeedDto> carSeedDtos);
    Car getRandomCar();
    //Query_2
    CarToyotaViewRootDto getAllToyotaCars();

    //Query_4
    CarWithPartsViewRootDto getAllCarsWithParts();
}

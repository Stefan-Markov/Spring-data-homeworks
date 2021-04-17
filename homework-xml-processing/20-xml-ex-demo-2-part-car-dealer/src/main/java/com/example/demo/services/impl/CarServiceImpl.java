package com.example.demo.services.impl;

import com.example.demo.models.dtos.CarSeedDto;
import com.example.demo.models.dtos.views.CarToyotaViewDto;
import com.example.demo.models.dtos.views.CarToyotaViewRootDto;
import com.example.demo.models.dtos.views.query4.CarWithPartsViewDto;
import com.example.demo.models.dtos.views.query4.CarWithPartsViewRootDto;
import com.example.demo.models.entities.Car;
import com.example.demo.models.entities.Part;
import com.example.demo.repositories.CarRepository;
import com.example.demo.services.CarService;
import com.example.demo.services.PartService;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PartService partService;
    private final Random random;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil, PartService partService, Random random) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.partService = partService;
        this.random = random;
    }

    @Override
    public void seedCars(List<CarSeedDto> carSeedDtos) {
        carSeedDtos
                .forEach(carSeedDto -> {
                    if (this.validationUtil.isValid(carSeedDto)) {
                        if (this.carRepository
                                .findByMakeAndModelAndTravelledDistance(
                                        carSeedDto.getMake(), carSeedDto.getModel(),
                                        carSeedDto.getTravelledDistance()) == null) {
                            Car car = this.modelMapper.map(carSeedDto, Car.class);

                            car.setParts(this.partService.getRandomParts());
                            this.carRepository.saveAndFlush(car);
                            System.out.println();
                        } else {
                            System.out.println("Car already in DB");
                        }

                    } else {
                        this.validationUtil
                                .getViolations(carSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Car getRandomCar() {
        long randomId = this.random.nextInt((int) this.carRepository.count()) + 1;

        return this.carRepository.getOne(randomId);
    }

    @Override
    public CarToyotaViewRootDto getAllToyotaCars() {
        CarToyotaViewRootDto carToyotaViewRootDto = new CarToyotaViewRootDto();
        List<CarToyotaViewDto> carToyotaViewDtos= this.carRepository
                .findAllByMakeEqualsOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream()
                .map(car -> this.modelMapper.map(car,CarToyotaViewDto.class))
                .collect(Collectors.toList());
        carToyotaViewRootDto.setCars(carToyotaViewDtos);
        return carToyotaViewRootDto;
    }

    @Override
    public CarWithPartsViewRootDto getAllCarsWithParts() {
        CarWithPartsViewRootDto carWithPartsViewRootDto =
                new CarWithPartsViewRootDto();
        //get all cars
        List<Car> cars = this.carRepository.findAll();
        List<CarWithPartsViewDto> carWithPartsViewDtos =
                this.carRepository.findAll()
                .stream()
                .map(car -> this.modelMapper.map(car,CarWithPartsViewDto.class))
                .collect(Collectors.toList());

        carWithPartsViewRootDto.setCars(carWithPartsViewDtos);
        return carWithPartsViewRootDto;
    }
}

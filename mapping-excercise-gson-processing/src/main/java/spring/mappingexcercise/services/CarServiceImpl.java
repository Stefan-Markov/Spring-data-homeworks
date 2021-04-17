package spring.mappingexcercise.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.mappingexcercise.domain.dtos.CarExportDto;
import spring.mappingexcercise.domain.dtos.CarSeedDto;
import spring.mappingexcercise.domain.entities.Car;
import spring.mappingexcercise.domain.entities.Part;
import spring.mappingexcercise.domain.repositories.CarRepository;
import spring.mappingexcercise.domain.repositories.PartRepository;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {
    private final static String CAR_PATH = "src/main/resources/jsons/cars.json";
    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ModelMapper modelMapper, Gson gson) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    @Transactional
    public void seedCars() throws Exception {
        String content = String.join("", Files.readAllLines(Path.of(CAR_PATH)));

        CarSeedDto[] carSeedDto = this.gson.fromJson(content, CarSeedDto[].class);

        for (CarSeedDto seedDto : carSeedDto) {
            Car car = this.modelMapper.map(seedDto, Car.class);
            car.setParts(getRandomParts());
            this.carRepository.saveAndFlush(car);

        }

    }

    @Override
    public String findByMaker() {
        Set<Car> maker = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");

        List<CarExportDto> exportCars = new ArrayList<>();
        for (Car car : maker) {
            CarExportDto carExport = this.modelMapper.map(car,CarExportDto.class);
            exportCars.add(carExport);
        }

        return this.gson.toJson(exportCars);
    }

    private Set<Part> getRandomParts() throws Exception {
        Set<Part> parts = new LinkedHashSet<>();
        for (int i = 0; i < 3; i++) {
            Part part = this.getRandomPart();
            parts.add(part);
        }
        return parts;
    }

    private Part getRandomPart() throws Exception {
        Random random = new Random();
        long index = (long) random.nextInt((int) this.partRepository.count()) + 1;
        Optional<Part> part = this.partRepository.findById(index);
        if (part.isPresent()) {
            return part.get();
        } else {
            throw new Exception("Invalid part Id!");
        }
    }
}

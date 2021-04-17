package spring.mappingexcercise.services;

import java.io.IOException;

public interface CarService {
    void seedCars() throws Exception;
    String findByMaker();
}

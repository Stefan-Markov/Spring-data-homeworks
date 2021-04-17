package spring.mappingexcercise.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.mappingexcercise.domain.entities.Car;
import spring.mappingexcercise.domain.entities.Customer;
import spring.mappingexcercise.domain.entities.Sale;
import spring.mappingexcercise.domain.repositories.CarRepository;
import spring.mappingexcercise.domain.repositories.CustomerRepository;
import spring.mappingexcercise.domain.repositories.SaleRepository;

import java.util.Random;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
    private SaleRepository saleRepository;
    private CarRepository carRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }

    @Override

    public void seedSales() {
        Sale sale = new Sale();
        sale.setCar(getRandomCar());
        sale.setCustomer(getRandomCustomer());
        sale.setDiscount(5);

        Sale sale1 = new Sale();
        sale1.setCar(getRandomCar());
        sale1.setCustomer(getRandomCustomer());
        sale1.setDiscount(10);


        Sale sale2 = new Sale();
        sale2.setCar(getRandomCar());
        sale2.setCustomer(getRandomCustomer());
        sale2.setDiscount(30);


        this.saleRepository.saveAndFlush(sale);
        this.saleRepository.saveAndFlush(sale1);
        this.saleRepository.saveAndFlush(sale2);

    }

    private Customer getRandomCustomer() {
        Random random = new Random();

        long id = (long) random.nextInt((int) this.customerRepository.count()) + 1;

        return this.customerRepository.findById(id).get();
    }

    private Car getRandomCar() {
        Random random = new Random();
        long id = (long) random.nextInt((int) this.carRepository.count()) + 1;
        return this.carRepository.findById(id).get();
    }
}

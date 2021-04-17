package com.example.demo.services.impl;

import com.example.demo.models.dtos.CustomerSeedDto;
import com.example.demo.models.dtos.views.CustomerViewDto;
import com.example.demo.models.dtos.views.CustomerViewRootDto;
import com.example.demo.models.dtos.views.query5.CustomerWithSalesViewDto;
import com.example.demo.models.dtos.views.query5.CustomerWithSalesViewRootDto;
import com.example.demo.models.entities.Customer;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.services.CustomerService;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Random random;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               ModelMapper modelMapper, ValidationUtil validationUtil,
                               Random random) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.random = random;
    }

    @Override
    public void seedCustomers(List<CustomerSeedDto> customerSeedDtoList) {
        customerSeedDtoList
                .forEach(customerSeedDto -> {
                    if (this.validationUtil.isValid(customerSeedDto)) {
                        if (this.customerRepository
                                .findByNameAndBirthDate(customerSeedDto.getName(),
                                        customerSeedDto.getBirthDate()) == null) {
                            Customer customer = this.modelMapper
                                    .map(customerSeedDto, Customer.class);
                            System.out.println();
                            this.customerRepository.saveAndFlush(customer);
                        } else {
                            System.out.println("Customer already in DB");
                        }
                    } else {
                        this.validationUtil.getViolations(customerSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Customer getRandomCustomer() {
        long randomId = this.random.nextInt((int) this.customerRepository.count()) + 1;

        return this.customerRepository.getOne(randomId);
    }

    //Query_1
    @Override
    public CustomerViewRootDto getAllOrderedCustomers() {
        CustomerViewRootDto customerViewRootDto = new CustomerViewRootDto();
        List<CustomerViewDto> customerViewDtos = this.customerRepository
                .findAllBirthDateAndIsYoungDriver()
                .stream()
                .map(customer -> this.modelMapper
                        .map(customer, CustomerViewDto.class))
                .collect(Collectors.toList());


        customerViewRootDto.setCustomers(customerViewDtos);
        return customerViewRootDto;
    }

    @Override
    public CustomerWithSalesViewRootDto getAllCustomersWithSales() {
        CustomerWithSalesViewRootDto customerWithSalesViewRootDto =
                new CustomerWithSalesViewRootDto();
        List<Object[]> customers = this.customerRepository.findAllBySales();
        List<CustomerWithSalesViewDto> customerWithSalesViewDtos =
                new ArrayList<>();
        for (Object[] customer : customers) {
            CustomerWithSalesViewDto customerWithSalesViewDto =
                    new CustomerWithSalesViewDto();
            customerWithSalesViewDto.setFullName((String) customer[0]);
            Object o = customer[1];
            BigInteger bigInteger = new BigInteger(String.valueOf(o));
            Integer integer = bigInteger.intValue();
            customerWithSalesViewDto.setBoughtCars(integer);
            customerWithSalesViewDto.setSpentMoney(new BigDecimal(String.valueOf(customer[2])));
            System.out.println();
            customerWithSalesViewDtos.add(customerWithSalesViewDto);
        }
        System.out.println();
        customerWithSalesViewRootDto.setCustomers(customerWithSalesViewDtos);
        return customerWithSalesViewRootDto;
    }


}

/*
// Another Solution Query_1
 @Override
    public CustomerViewRootDto getAllOrderedCustomers() {
        CustomerViewRootDto customerViewRootDto = new CustomerViewRootDto();
        List<Customer> customers = this.customerRepository
                .findAllBirthDateAndIsYoungDriver();
        List<CustomerViewDto> customerViewDtos = new ArrayList<>();
        customers
                .forEach(customer -> {
                    CustomerViewDto customerViewDto = this.modelMapper.map(customer, CustomerViewDto.class);
                    customerViewDtos.add(customerViewDto);
                });
        customerViewRootDto.setCustomers(customerViewDtos);
        return customerViewRootDto;
    }
 */
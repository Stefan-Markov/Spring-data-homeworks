package spring.mappingexcercise.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.mappingexcercise.domain.dtos.CustomerExportDto;
import spring.mappingexcercise.domain.dtos.CustomerSeedDto;
import spring.mappingexcercise.domain.entities.Customer;
import spring.mappingexcercise.domain.repositories.CustomerRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final static String CUSTOMER_PATH = "src/main/resources/jsons/customers.json";
    private final CustomerRepository customerRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, Gson gson, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCustomers() throws IOException {
        String content = String.join("",
                Files.readAllLines(Path.of(CUSTOMER_PATH)));

        CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(content, CustomerSeedDto[].class);

        for (CustomerSeedDto customerSeedDto : customerSeedDtos) {
            Customer customer = this.modelMapper.map(customerSeedDto, Customer.class);
            this.customerRepository.saveAndFlush(customer);
        }
    }

    @Override
    public String orderCustomers() {

        Set<Customer> customers = this.customerRepository.getAllByOrderByYoungDriverAscBirthDateAsc();

        List<CustomerExportDto> customerDtos = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerExportDto customerDto = this.modelMapper.map(customer, CustomerExportDto.class);
            customerDtos.add(customerDto);
        }
        return this.gson.toJson(customerDtos);

    }
}

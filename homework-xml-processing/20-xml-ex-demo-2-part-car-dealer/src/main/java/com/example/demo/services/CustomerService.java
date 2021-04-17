package com.example.demo.services;

import com.example.demo.models.dtos.CustomerSeedDto;
import com.example.demo.models.dtos.views.CustomerViewRootDto;
import com.example.demo.models.dtos.views.query5.CustomerWithSalesViewRootDto;
import com.example.demo.models.entities.Customer;

import java.util.List;

public interface CustomerService {
    public void seedCustomers(List<CustomerSeedDto> customerSeedDtoList);
    Customer getRandomCustomer();
    CustomerViewRootDto getAllOrderedCustomers();
    //Query_5
    CustomerWithSalesViewRootDto getAllCustomersWithSales();
}

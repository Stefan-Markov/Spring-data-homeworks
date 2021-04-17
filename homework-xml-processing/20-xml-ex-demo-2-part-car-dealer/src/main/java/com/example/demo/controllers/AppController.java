package com.example.demo.controllers;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.models.dtos.*;
import com.example.demo.models.dtos.views.CarToyotaViewRootDto;
import com.example.demo.models.dtos.views.CustomerViewRootDto;
import com.example.demo.models.dtos.views.LocalSupplierViewRootDto;
import com.example.demo.models.dtos.views.query4.CarWithPartsViewRootDto;
import com.example.demo.models.dtos.views.query5.CustomerWithSalesViewRootDto;
import com.example.demo.models.dtos.views.query6.SaleViewRootDto;
import com.example.demo.services.*;
import com.example.demo.utils.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

import static com.example.demo.constants.GlobalConstants.*;

@Component
public class AppController implements CommandLineRunner {

    private final SupplierService supplierService;
    private final XmlParser xmlParser;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    @Autowired
    public AppController(SupplierService supplierService, XmlParser xmlParser, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.supplierService = supplierService;
        this.xmlParser = xmlParser;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
//
//        this.seedSuppliers();
//        this.seedParts();
//        this.seedCars();
//        this.seedCustomers();
//        this.seedSales();
        //Query_1
//        this.writeOrderedCustomers();
        //Query_2
//        this.writeToyotaCars();
        //Query_3
//        this.writeLocalSuppliers();
        //Query_4
//        this.writeAllCarsWithParts();
        //Query_5
//        this.writeCustomerTotalSales();
        //Query_6
        this.writeAllSalesWithDiscounts();
    }

    //Query_6
    private void writeAllSalesWithDiscounts() throws JAXBException {
        SaleViewRootDto saleViewRootDto =
                this.saleService.getAllSales();
        this.xmlParser.marshalToFile(EX_4_QUERY_6_SALES_DISCOUNTS_FILE_PATH,
                saleViewRootDto);
    }

    //Query_5
    private void writeCustomerTotalSales() throws JAXBException {
        CustomerWithSalesViewRootDto customerWithSalesViewRootDto =
                this.customerService.getAllCustomersWithSales();
        this.xmlParser.marshalToFile(EX_4_QUERY_5_CUSTOMERS_TOTAL_SALES_FILE_PATH,
                customerWithSalesViewRootDto);
    }

    private void writeAllCarsWithParts() throws JAXBException {
        CarWithPartsViewRootDto carWithPartsViewRootDto =
                this.carService.getAllCarsWithParts();
        this.xmlParser.marshalToFile(EX_4_QUERY_4_CARS_AND_PARTS_FILE_PATH,
                carWithPartsViewRootDto);
    }

    //Query_3
    private void writeLocalSuppliers() throws JAXBException {
        LocalSupplierViewRootDto localSupplierViewRootDto =
                this.supplierService.getAllLocalSuppliers();
        this.xmlParser.marshalToFile(EX_4_QUERY_3_LOCAL_SUPPLIERS_FILE_PATH,
                        localSupplierViewRootDto);
    }

    //Query_2
    private void writeToyotaCars() throws JAXBException {
        CarToyotaViewRootDto carToyotaViewRootDto
                =this.carService.getAllToyotaCars();
        this.xmlParser.marshalToFile(EX_4_QUERY_2_TOYOTA_CARS_FILE_PATH,carToyotaViewRootDto);
    }

    //Query_1
    private void writeOrderedCustomers() throws JAXBException {
        CustomerViewRootDto customerViewRootDto = this
                .customerService.getAllOrderedCustomers();
        this.xmlParser.marshalToFile(EX_4_QUERY_1_ORDERED_CUSTOMERS_FILE_PATH, customerViewRootDto);
    }

    private void seedSales() {
        this.saleService.seedSales();
    }

    private void seedCustomers() throws JAXBException, FileNotFoundException {
        CustomerSeedRootDto customerSeedRootDto = this.xmlParser
                .unmarshalFromFile(CUSTOMERS_FILE_PATH, CustomerSeedRootDto.class);

        System.out.println();
        this.customerService.seedCustomers(customerSeedRootDto.getCustomers());
    }

    private void seedCars() throws JAXBException, FileNotFoundException {
        CarSeedRootDto carSeedRootDto = this.xmlParser
                .unmarshalFromFile(CARS_FILE_PATH, CarSeedRootDto.class);
        System.out.println();
        this.carService.seedCars(carSeedRootDto.getCars());
    }

    private void seedParts() throws JAXBException, FileNotFoundException {
        PartSeedRootDto partSeedRootDto = this.xmlParser
                .unmarshalFromFile(PARTS_FILE_PATH, PartSeedRootDto.class);
        List<PartSeedDto> partSeedDtos = partSeedRootDto.getParts();
        this.partService.seedParts(partSeedDtos);
    }

    private void seedSuppliers() throws JAXBException, FileNotFoundException {

        SupplierSeedRootDto supplierSeedRootDto = this.xmlParser
                .unmarshalFromFile(GlobalConstants.SUPPLIERS_FILE_PATH,
                        SupplierSeedRootDto.class);

        List<SupplierSeedDto> suppliers = supplierSeedRootDto.getSuppliers();
        this.supplierService.seedSuppliers(suppliers);
    }


}

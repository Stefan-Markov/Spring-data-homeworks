package com.example.demo.services.impl;

import com.example.demo.models.dtos.views.query6.SaleViewDto;
import com.example.demo.models.dtos.views.query6.SaleViewRootDto;
import com.example.demo.models.entities.Part;
import com.example.demo.models.entities.Sale;
import com.example.demo.repositories.SaleRepository;
import com.example.demo.services.CarService;
import com.example.demo.services.CustomerService;
import com.example.demo.services.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final Random random;
    private final ModelMapper modelMapper;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarService carService, CustomerService customerService, Random random, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.random = random;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSales() {
        for (int i = 0; i < 20; i++) {
            Sale sale = new Sale();
            sale.setDiscount(this.getRandomDiscount());
            sale.setCar(this.carService.getRandomCar());
            sale.setCustomer(this.customerService.getRandomCustomer());
            System.out.println();
            this.saleRepository.saveAndFlush(sale);
        }
    }

    @Override
    public SaleViewRootDto getAllSales() {
        SaleViewRootDto saleViewRootDto = new SaleViewRootDto();
        List<Sale> all = this.saleRepository.findAll();
        List<SaleViewDto> saleViewDtos = this.saleRepository.findAll()
                .stream()
                .map(sale -> {
                    SaleViewDto saleViewDto =
                    this.modelMapper.map(sale, SaleViewDto.class);
                    Double discount = sale.getDiscount();
                    Set<Part> saleParts = sale.getCar().getParts();
                    BigDecimal totalPrice = BigDecimal.ZERO;

                    for (Part part : saleParts) {
                        totalPrice = totalPrice.add(part.getPrice());
                    }

                    saleViewDto.setPrice(totalPrice);
                    saleViewDto.setPriceWithDiscount(totalPrice
                            .subtract(totalPrice.multiply(BigDecimal.valueOf(discount))));
                    return saleViewDto;
                })
                .collect(Collectors.toList());

        saleViewRootDto.setSales(saleViewDtos);
        return saleViewRootDto;
    }

    private Double getRandomDiscount() {
        Double[] discounts = new Double[]{0D, 0.05, 0.1, 0.2, 0.3, 0.4, 0.5};
        // return from 0 - 7 index
        return discounts[this.random.nextInt(discounts.length)];
    }
}

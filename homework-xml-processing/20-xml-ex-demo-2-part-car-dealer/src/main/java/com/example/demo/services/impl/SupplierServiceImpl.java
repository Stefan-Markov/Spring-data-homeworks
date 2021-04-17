package com.example.demo.services.impl;

import com.example.demo.models.dtos.SupplierSeedDto;
import com.example.demo.models.dtos.views.LocalSupplierViewDto;
import com.example.demo.models.dtos.views.LocalSupplierViewRootDto;
import com.example.demo.models.entities.Supplier;
import com.example.demo.repositories.SupplierRepository;
import com.example.demo.services.SupplierService;
import com.example.demo.utils.ValidationUtil;
import com.example.demo.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {


    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final Random random;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository
            , ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, Random random) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.random = random;
    }

    @Override
    public void seedSuppliers(List<SupplierSeedDto> supplierSeedDtos) throws JAXBException, FileNotFoundException {
        supplierSeedDtos
                .forEach(supplierSeedDto -> {
                    if(this.validationUtil.isValid(supplierSeedDto)){
                        // if such supplier does not exists in the db table
                        if(this.supplierRepository
                                .findByName(supplierSeedDto.getName()) == null){
                            Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
                            this.supplierRepository.saveAndFlush(supplier);
                        }else {
                            // if such supplier already exists in the db table
                            System.out.println("A supplier with this name already exists");
                        }
                    }else{
                        this.validationUtil
                                .getViolations(supplierSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Supplier getRandomSupplier() {
        long randomId = this.random.nextInt((int) this
                .supplierRepository.count()) + 1;
        Supplier supplier = this.supplierRepository.getOne(randomId);
        return supplier;
    }

    @Override
    public LocalSupplierViewRootDto getAllLocalSuppliers() {
        LocalSupplierViewRootDto localSupplierViewRootDto = new LocalSupplierViewRootDto();
        List<LocalSupplierViewDto> localSupplierViewDtos =
        this.supplierRepository.findAllByImporterIsFalse()
                    .stream()
                    .map(supplier -> {
                        LocalSupplierViewDto localSupplierViewDto =
                        this.modelMapper.map(supplier, LocalSupplierViewDto.class);
                        localSupplierViewDto.setPartsCount(supplier.getParts().size());
                        return localSupplierViewDto;

                    }).collect(Collectors.toList());
        localSupplierViewRootDto.setSuppliers(localSupplierViewDtos);
        return localSupplierViewRootDto;
    }
}

package com.example.demo.services;

import com.example.demo.models.dtos.SupplierSeedDto;
import com.example.demo.models.dtos.SupplierSeedRootDto;
import com.example.demo.models.dtos.views.LocalSupplierViewRootDto;
import com.example.demo.models.entities.Supplier;
import com.example.demo.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public interface SupplierService {
    void seedSuppliers(List<SupplierSeedDto> supplierSeedDtoList)
            throws JAXBException, FileNotFoundException;

    Supplier getRandomSupplier();

    //Query_3
    LocalSupplierViewRootDto getAllLocalSuppliers();
}

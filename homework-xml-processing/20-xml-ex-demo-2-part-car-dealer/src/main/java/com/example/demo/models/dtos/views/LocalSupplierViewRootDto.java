package com.example.demo.models.dtos.views;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocalSupplierViewRootDto {
    @XmlElement(name = "supplier")
    private List<LocalSupplierViewDto> suppliers;

    public LocalSupplierViewRootDto() {
    }

    public List<LocalSupplierViewDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<LocalSupplierViewDto> suppliers) {
        this.suppliers = suppliers;
    }
}

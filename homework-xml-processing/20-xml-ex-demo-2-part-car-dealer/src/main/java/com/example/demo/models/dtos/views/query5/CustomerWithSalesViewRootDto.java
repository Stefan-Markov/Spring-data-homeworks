package com.example.demo.models.dtos.views.query5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerWithSalesViewRootDto {
    @XmlElement(name = "customer")
    private List<CustomerWithSalesViewDto> customers;

    public CustomerWithSalesViewRootDto() {
    }

    public List<CustomerWithSalesViewDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerWithSalesViewDto> customers) {
        this.customers = customers;
    }
}

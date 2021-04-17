package com.example.demo.models.dtos.views;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarToyotaViewRootDto {
    @XmlElement(name = "car")
    private List<CarToyotaViewDto> cars;

    public CarToyotaViewRootDto() {
    }

    public List<CarToyotaViewDto> getCars() {
        return cars;
    }

    public void setCars(List<CarToyotaViewDto> cars) {
        this.cars = cars;
    }
}

package spring.mappingexcercise.domain.dtos;

import com.google.gson.annotations.Expose;

public class CarSeedDto {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private long travelDistance;

    public CarSeedDto() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(long travelDistance) {
        this.travelDistance = travelDistance;
    }
}

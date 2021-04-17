package spring.mappingexcercise.domain.dtos;

import com.google.gson.annotations.Expose;

public class CarExportDto {
    @Expose
    private long id;
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private int travelledDistance;

    public CarExportDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(int travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}

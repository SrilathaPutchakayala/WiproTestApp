package com.wiprotestapp;

/**
 * Created by admin on 27-Jul-16.
 */
public class Transport {

    private String name;
    private String id;
    private String fromAddress;
    private String transportModeByCar;
    private String transportModeByTrain;
    private float latitude;
    private float longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getTransportModeByTrain() {
        return transportModeByTrain;
    }

    public void setTransportModeByTrain(String transportModeByTrain) {
        this.transportModeByTrain = transportModeByTrain;
    }

    public String getTransportModeByCar() {
        return transportModeByCar;
    }

    public void setTransportModeByCar(String transportModeByCar) {
        this.transportModeByCar = transportModeByCar;
    }
}

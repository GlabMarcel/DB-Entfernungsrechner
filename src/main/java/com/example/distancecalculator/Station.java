package com.example.distancecalculator;

/**
 * This class defines a station based on the description from the task.
 * It contains getter and setter functions
 */
public class Station {

    private Integer EVA_NR, operatorID;
    private String DS100;
    private String IFOPT;
    private String name;
    private String traffic;
    private String operatorName;
    private String status;
    private Double longitude, latitude;

    public Station(Integer EVA_NR, Integer operatorID, String DS100, String IFOPT, String name, String traffic, String operatorName, String status, Double longitude, Double latitude) {
        this.EVA_NR = EVA_NR;
        this.operatorID = operatorID;
        this.DS100 = DS100;
        this.IFOPT = IFOPT;
        this.name = name;
        this.traffic = traffic;
        this.operatorName = operatorName;
        this.status = status;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public Integer getEVA_NR() {
        return EVA_NR;
    }

    public void setEVA_NR(int EVA_NR) {
        this.EVA_NR = EVA_NR;
    }

    public Integer getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(int operatorID) {
        this.operatorID = operatorID;
    }

    public String getDS100() {
        return DS100;
    }

    public void setDS100(String DS100) {
        this.DS100 = DS100;
    }

    public String getIFOPT() {
        return IFOPT;
    }

    public void setIFOPT(String IFOPT) {
        this.IFOPT = IFOPT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

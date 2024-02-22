package com.example.flexcity_api;

import java.util.List;

class Asset {
    private String code;
    private String name;
    private double activationCost;
    private List<String> availability;
    private int volume;

    public Asset(String code, String name, double activationCost, List<String> availability, int volume) {
        this.code = code;
        this.name = name;
        this.activationCost = activationCost;
        this.availability = availability;
        this.volume = volume;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getActivationCost() {
        return activationCost;
    }

    public void setActivationCost(double activationCost) {
        this.activationCost = activationCost;
    }

    public List<String> getAvailability() {
        return availability;
    }

    public void setAvailability(List<String> availability) {
        this.availability = availability;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

}
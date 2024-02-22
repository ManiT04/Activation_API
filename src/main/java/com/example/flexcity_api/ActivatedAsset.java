package com.example.flexcity_api;

class ActivatedAsset {
    private String code;
    private int activatedVolume;
    private double activationCost;

    public ActivatedAsset(String code, int activatedVolume, double activationCost) {
        this.code = code;
        this.activatedVolume = activatedVolume;
        this.activationCost = activationCost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getActivatedVolume() {
        return activatedVolume;
    }

    public void setActivatedVolume(int activatedVolume) {
        this.activatedVolume = activatedVolume;
    }

    public double getActivationCost() {
        return activationCost;
    }

    public void setActivationCost(double activationCost) {
        this.activationCost = activationCost;
    }
}


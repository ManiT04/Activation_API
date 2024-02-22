package com.example.flexcity_api;

class ActivationRequest {
    private String date;
    private int volume;

    public ActivationRequest(String date, int volume) {
        this.date = date;
        this.volume = volume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
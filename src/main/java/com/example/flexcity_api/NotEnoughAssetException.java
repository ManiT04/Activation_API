package com.example.flexcity_api;

public class NotEnoughAssetException extends Exception {
    public NotEnoughAssetException(String reason) {
        super(reason);
    }
}

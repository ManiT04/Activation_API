package com.example.flexcity_api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/activation")
class ActivationController {

    private final List<Asset> assets = createAssetList(); // Hardcoded list of assets

    @PostMapping("/activate")
    public ResponseEntity<List<DesactivatedAsset>> activateAssets(@RequestBody ActivationRequest activationRequest)
            throws NotEnoughAssetException {

        // Extract information from the activation request
        String activationDate = activationRequest.getDate();
        int requestedVolume = activationRequest.getVolume();

        // Filter assets based on availability and sort by activation cost
        List<Asset> availableAssets = filterAvailableAssets(activationDate, assets);
        availableAssets.sort(Comparator.comparingDouble(Asset::getActivationCost));

        // Display available asset
        for(Asset a: availableAssets) {
            System.out.println(a.getName() + " => " + a.getActivationCost());
        }

        // Calculate the total activated volume and check if it's enough
        List<DesactivatedAsset> deactivateAssets = new ArrayList<>();
        int volumeToActivate = requestedVolume;
        int totalActivatedVolume = 0;

        for (Asset asset : availableAssets) {
            if (volumeToActivate > 0) {
                volumeToActivate -= asset.getVolume();
                totalActivatedVolume += asset.getVolume();
                deactivateAssets.add(new DesactivatedAsset(asset.getCode(), totalActivatedVolume, asset.getActivationCost()));
            }

            if (totalActivatedVolume >= requestedVolume) {
                return ResponseEntity.ok(deactivateAssets);
            }
        }

        if(volumeToActivate > 0)
            throw new NotEnoughAssetException("Not enough asset available...");

        return ResponseEntity.ok(null);
    }

    private static boolean filterAsset(String activationDate, Asset asset) {
        final List<String> availabilities = asset.getAvailability();
        for (String a : availabilities) {
            if (a.equalsIgnoreCase(activationDate))
                return true;
        }
        return false;
    }

    private static List<Asset> filterAvailableAssets(String activationDate, List<Asset> assets) {
        final LocalDate date = LocalDate.parse(activationDate);
        final String finalActivationDate = date.getDayOfWeek().toString();
        System.out.println("Day : " + finalActivationDate);

        return assets.stream()
                .filter(a -> filterAsset(finalActivationDate, a))
                .collect(Collectors.toList());
    }

    private static List<Asset> createAssetList() {
        // Hardcoded list of assets
        List<Asset> assetList = new ArrayList<>();
        assetList.add(new Asset("A1", "Asset 1", 10.0, List.of("Monday"), 50));
        assetList.add(new Asset("A2", "Asset 2", 8.0, List.of("Monday", "Tuesday"), 30));
        assetList.add(new Asset("A3", "Asset 3", 12.0, List.of("Monday", "Wednesday"), 40));
        assetList.add(new Asset("A4", "Asset 4", 15.0, List.of("Thursday", "Friday"), 20));
        assetList.add(new Asset("A5", "Asset 5", 17.0, List.of("Tuesday", "Wednesday", "Thursday"), 12));
        assetList.add(new Asset("A6", "Asset 6", 9.0, List.of("Tuesday", "Thursday", "Friday"), 70));
        return assetList;
    }
}






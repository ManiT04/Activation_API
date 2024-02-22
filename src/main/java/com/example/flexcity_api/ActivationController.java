package com.example.flexcity_api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@RestController
@RequestMapping("/activation")
class ActivationController {

    private List<Asset> assets = createAssetList(); // Hardcoded list of assets

    @PostMapping("/activate")
    public ResponseEntity<List<ActivatedAsset>> activateAssets(@RequestBody ActivationRequest activationRequest)
            throws ParseException, NotEnoughAssetError {

        // Extract information from the activation request
        String activationDate = activationRequest.getDate();
        int requestedVolume = activationRequest.getVolume();

        // Filter assets based on availability and sort by activation cost
        // TODO filter with availability date
        List<Asset> availableAssets = filterAvailableAssets(activationDate);
        // TODO sort by activation_cost
        //availableAssets.sort((a1, a2) -> Double.compare(a1.getActivationCost(), a2.getActivationCost()));
        availableAssets.sort(Comparator.comparingDouble(Asset::getActivationCost));
        System.out.println(availableAssets);

        // Calculate the total activated volume and check if it's enough
        // TODO
        int totalActivatedVolume = 0;
        List<ActivatedAsset> activatedAssets = new ArrayList<>();

        for (Asset asset : availableAssets) {
            int remainingVolume = asset.getVolume() - totalActivatedVolume;
            int volumeToActivate = Math.min(remainingVolume, requestedVolume);

            if (volumeToActivate > 0) {
                // TODO
                activatedAssets.add(new ActivatedAsset(asset.getCode(), volumeToActivate, asset.getActivationCost()));
                totalActivatedVolume += volumeToActivate;
            }

            if (totalActivatedVolume >= requestedVolume) {
                return ResponseEntity.ok(activatedAssets);
            } else if(asset == availableAssets.get(activatedAssets.size()-1)) {
                throw new NotEnoughAssetError("Not enough asset available...");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    private List<Asset> filterAvailableAssets(String activationDate) throws ParseException {
        // TODO

        int availability = 0;

        /**LocalDate Method 1**/
        LocalDate date = LocalDate.parse(activationDate);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String finalActivationDate = dayOfWeek.toString();

        /**LocalDate Method 2**/
        String finalActivationDate2 = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        /**Date Method 1**/
        DateFormat formatter = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        Date formatDate = formatter.parse(activationDate);
        String finalActivationDate3 =  formatter.format(activationDate);

        /**Date Method 2**/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDate);
        int intDay = calendar.get(Calendar.DAY_OF_MONTH);
        String finalActivationDate4 = Day.getByValue(intDay);


        for (Asset asset : assets) {
            for (String d : asset.getAvailability()) {
                if(d.equals(finalActivationDate)) {
                    availability = 1;
                }
            }

            if (availability != 1) {
                assets.remove(asset);
            }

            availability = 0;
            System.out.println(asset);
        }

        return assets;
    }

    private static List<Asset> createAssetList() {
        // Hardcoded list of assets
        List<Asset> assetList = new ArrayList<>();
        assetList.add(new Asset("A1", "Asset 1", 10.0, List.of("Monday"), 50));
        assetList.add(new Asset("A2", "Asset 2", 8.0, List.of("Monday", "Tuesday"), 30));
        assetList.add(new Asset("A3", "Asset 3", 12.0, List.of("Monday", "Wednesday"), 40));
        assetList.add(new Asset("A4", "Asset 4", 15.0, List.of("Thursday, Friday"), 20));
        assetList.add(new Asset("A5", "Asset 5", 17.0, List.of("Tuesday", "Wednesday", "Thursday"), 12));
        assetList.add(new Asset("A6", "Asset 6", 9.0, List.of("Tuesday", "Thursday", "Friday"), 70));
        return assetList;
    }
}






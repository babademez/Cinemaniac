package com.example.berke.databaseloginregister;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by berke on 29.07.2017.
 */

public class MapDataParser {

    private HashMap< String, String> getPlace(JSONObject googlePlaceJson){
        HashMap< String, String> googlePlacesMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";


        try {
            if( !googlePlaceJson.isNull("name")){

                placeName = googlePlaceJson.getString("name");
            }
            if( !googlePlaceJson.isNull("vicinity")){

                vicinity = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlaceJson.getString("reference");

            googlePlacesMap.put("place_name", placeName);
            googlePlacesMap.put("vicinity", vicinity);
            googlePlacesMap.put("lat", latitude);
            googlePlacesMap.put("lng", longitude);
            googlePlacesMap.put("reference", reference);

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlacesMap;

    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for (int i =0; i < count; i++)
        {
            try {
                placeMap = getPlace( (JSONObject) jsonArray.get(i)); //fetch each elmenet from the array for each place and it will store them in places list
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return placesList;
    }
    // when we create dataparser we will call this method. It will parse the JSon data and send it to getPlaces method
    public List<HashMap<String, String>> parse( String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);

    }
}

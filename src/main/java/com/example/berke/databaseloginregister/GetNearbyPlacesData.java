package com.example.berke.databaseloginregister;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by berke on 29.07.2017.
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String>{

    String googlePlacesData;
    GoogleMap mMap;
    String url;
    private ArrayList<MarkerOptions> markerOptionses;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects [0];
        url = (String) objects[1];

        MapDownloadUrl mapDownloadUrl = new MapDownloadUrl();
        try {
            googlePlacesData = mapDownloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }


    @Override
    protected void onPostExecute(String s ){

        List<HashMap<String, String>> nearbPlacesList = null;
        MapDataParser mapDataParser = new MapDataParser();
        nearbPlacesList = mapDataParser.parse(s);
        showNearbyPlaces(nearbPlacesList);
    }

    // shows all the places in the map
    private void showNearbyPlaces(List<HashMap<String, String>> nearbPlacesList)
    {
        markerOptionses = new ArrayList<>();
        for( int i = 0; i < nearbPlacesList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbPlacesList.get(i);

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            // it returns a string so we need to parse it to double
            double  lat = Double.parseDouble( googlePlace.get("lat"));
            double lng = Double.parseDouble( googlePlace.get("lng"));

            LatLng latLng = new LatLng( lat, lng);
            markerOptions.position(latLng);
            markerOptions.title( placeName + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_VIOLET));

            mMap.addMarker( markerOptions);
            markerOptionses.add(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera( CameraUpdateFactory.zoomTo(10));
        }
    }

    public ArrayList getMarkerList()
    {
        return markerOptionses;
    }
}

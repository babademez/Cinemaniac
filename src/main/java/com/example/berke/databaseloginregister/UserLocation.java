package com.example.berke.databaseloginregister;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserLocation extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    public static String theaterName;
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    private Button theaterLocationButton;
    private ArrayList<MarkerOptions> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        theaterLocationButton = (Button) findViewById(R.id.findMovieTheaterLocationTest);
        theaterLocationButton.setOnClickListener(new findTheaterLocationsButtonListener());


        //if( mMap != null)
            //mMap.setOnMarkerClickListener( new theaterMarkerClickListener());
    }

    /**
     * public void onClick( View v)
     * {
     * <p>
     * if(v.getId() == R.id.locationSearchButtonTest)
     * {
     * EditText testLocation = (EditText)findViewById(R.id.InputLocationTest);
     * String location = testLocation.getText().toString();
     * List<Address> addressList = null;
     * MarkerOptions mo = new MarkerOptions();
     * <p>
     * if( !location.equals(""))// checks if the user entered something
     * {
     * Geocoder geocoder = new Geocoder(this);
     * <p>
     * try {
     * addressList = geocoder.getFromLocationName(location, 5);
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * <p>
     * for(int i = 0; i < addressList.size(); i++)
     * {
     * Address myAddress = addressList.get(addressList.size()-1);
     * LatLng latLng = new LatLng( myAddress.getLatitude(), myAddress.getLongitude());
     * mo.position(latLng);
     * mo.title("Search Result");
     * mMap.addMarker(mo);
     * mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
     * }
     * }
     * }
     * <p>
     * if( v.getId() == R.id.findMovieTheaterLocationTest)
     * {
     * mMap.clear();
     * String theater = "movie_theater";
     * String url = getUrl(latitude, longitude, theater);
     * Object dataTransfer[] = new Object[ 2];
     * dataTransfer[0] = mMap;
     * dataTransfer[1] = url;
     * <p>
     * GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
     * getNearbyPlacesData.execute(dataTransfer);
     * Toast.makeText(UserLocation.this,"Showing nearby theaters bitch!", Toast.LENGTH_LONG).show();
     * }
     * }
     */
    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location=" + latitude + "," + longitude);
        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type=" + nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key=" + "AIzaSyCJ_AyXVXushmDjC10G_fPfxm-KfL_M1EM");

        return googlePlaceUrl.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission is granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else { // permission is denied
                    Toast.makeText(this, "Permission denied buddy!", Toast.LENGTH_LONG).show();
                }

        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


    }

    // we created a google api client
    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

        lastLocation = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        if (currentLocationMarker != null) // if there is already a marker we delete it
        {
            currentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions(); // to set the properies of the marker
        markerOptions.position(latLng);
        markerOptions.title("MyLocation");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10)); // experiment with new values
        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);// in ms
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) { // returns true if the app has requested this permission previously
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;

        } else
            return true;
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class findTheaterLocationsButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mMap.clear();
            String theater = "movie_theater";
            String url = getUrl(latitude, longitude, theater);
            Object dataTransfer[] = new Object[2];
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;

            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
            getNearbyPlacesData.execute(dataTransfer);
            markerList = getNearbyPlacesData.getMarkerList();
            mMap.setOnMarkerClickListener( new theaterMarkerClickListener());
            Toast.makeText(UserLocation.this, "Showing nearby theaters!", Toast.LENGTH_LONG).show();
        }
    }

    private class theaterMarkerClickListener implements GoogleMap.OnMarkerClickListener {
        @Override
        public boolean onMarkerClick(Marker marker) {

            theaterName = marker.getTitle();
            startActivity( new Intent( UserLocation.this, ParseActivity.class));
            return false;
        }
    }
}

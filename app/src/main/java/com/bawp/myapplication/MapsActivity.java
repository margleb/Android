package com.bawp.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "Maps";
    private GoogleMap mMap;
    private LatLng mountainEverest = new LatLng(27.9881388,86.9162203);
    private LatLng mountainKilimanjaro = new LatLng(-3.0674031,37.3468725);
    private LatLng mountainAlps = new LatLng(45.830072,6.1828847);

    private MarkerOptions everestOptions;
    private MarkerOptions theAlpsOptions;
    private MarkerOptions theKilimanjaroOptions;

    private List<MarkerOptions> markerOptionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        markerOptionsList = new ArrayList<>();

        everestOptions = new MarkerOptions().position(mountainEverest).
                title("Everest").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        markerOptionsList.add(everestOptions);

        theAlpsOptions = new MarkerOptions().position(mountainAlps).
                title("Alps").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        markerOptionsList.add(theAlpsOptions);

        theKilimanjaroOptions = new MarkerOptions().position(mountainKilimanjaro).
                title("Alps").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        markerOptionsList.add(theKilimanjaroOptions);

        for(MarkerOptions options : markerOptionsList) {
            LatLng latLng = new LatLng(options.getPosition().latitude, options.getPosition().longitude);
            mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 4));
        }
    }
}

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "Maps";
    private GoogleMap mMap;
    private LatLng mountainEverest = new LatLng(27.9881388,86.9162203);
    private LatLng mountainKilimanjaro = new LatLng(-3.0674031,37.3468725);
    private LatLng mountainAlps = new LatLng(45.830072,6.1828847);
    private ArrayList<Marker> markerArrayList;

    // Todo: Create Markers for each mountain
    private Marker everestMarker;
    private Marker kelimanjaroMarker;
    private Marker theAlpsMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        markerArrayList = new ArrayList<>();

        kelimanjaroMarker = mMap.addMarker(new MarkerOptions()
                .position(mountainKilimanjaro)
                .title("Mt Kelimanjaro")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        markerArrayList.add(kelimanjaroMarker);
        theAlpsMarker = mMap.addMarker(new MarkerOptions()
                .position(mountainAlps)
                .title("The Alps")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        markerArrayList.add(theAlpsMarker);
        everestMarker = mMap.addMarker(new MarkerOptions()
                .position(mountainEverest)
                .title("Mt. Everest")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        markerArrayList.add(everestMarker);

        for(Marker marker : markerArrayList) {
            LatLng latLng = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 4));
        }

        // Add a marker in Sydney and move the camera
//        LatLng winterPalace = new LatLng(59.9403985,30.3116075);
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(winterPalace).title("Marker of Winter Palace at St.Petersbourg")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
//                .alpha(0.8f));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(winterPalace, 9)); // 1 - 20
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(winterPalace));
    }
}

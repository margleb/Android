package com.bawp.myproject;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bawp.myproject.Model.EarthQuake;
import com.bawp.myproject.UI.Constants;
import com.bawp.myproject.UI.CustomInfoWindow;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    LocationListener locationListener;
    LocationManager locationManager;
    private RequestQueue queue;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        queue = Volley.newRequestQueue(this);
        getEarthQuakes();
    }

    private void getEarthQuakes() {
        final EarthQuake earthQuake = new EarthQuake();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray featues = response.getJSONArray("features");
                    for(int i = 0; i < Constants.LIMIT; i++) {
                        JSONObject properties = featues.getJSONObject(i).getJSONObject("properties");
                        // Log.d("Properties:", properties.getString("place"));
                        // Get geometry object
                        JSONObject geometry = featues.getJSONObject(i).getJSONObject("geometry");
                        // get coordinates array
                        JSONArray coordinates = geometry.getJSONArray("coordinates");
                        double lon = coordinates.getDouble(0);
                        double lat = coordinates.getDouble(1);
                        // Log.d("Quake",lon + ", " + lat);
                        earthQuake.setPlace(properties.getString("place"));
                        earthQuake.setType(properties.getString("type"));
                        earthQuake.setTime(properties.getLong("time"));
                        earthQuake.setMagnitude(properties.getDouble("mag"));
                        earthQuake.setDetailLink(properties.getString("detail"));
                        java.text.DateFormat dateFormat = java.text.DateFormat.getInstance();

                        String formattedDate = dateFormat.format(new Date(Long.valueOf(properties.getLong("time"))).getTime());
                        // добавляем маркеры
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                        markerOptions.title(earthQuake.getPlace());
                        markerOptions.position(new LatLng(lat, lon));
                        markerOptions.snippet("Magnitude: " + earthQuake.getMagnitude() + "\n" + "Date: " + formattedDate);

                        Marker marker = mMap.addMarker(markerOptions);
                        marker.setTag(earthQuake.getDetailLink());

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 1));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Quake","error!");
            }
        });
        queue.add(jsonObjectRequest);
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

        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(this);

        // регистрируем менеджер расположения
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        // регистрируем слушатель
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Location", "onLocationChanged: " + location.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // уведомление
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, locationListener);
            }
        }

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, locationListener);
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        // передаем url detail
        getQuakeDetails(marker.getTag().toString());
        // Toast.makeText(getApplicationContext(), marker.getTag().toString(), Toast.LENGTH_LONG).show();

    }

    private void getQuakeDetails(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String detailsUrl = "в";
                try {
                    JSONObject properties = response.getJSONObject("properties");
                    JSONObject products = properties.getJSONObject("products");
                    JSONArray geoserve = products.getJSONArray("geoserve");
                    for(int i = 0; i < geoserve.length(); i++) {
                        JSONObject geoserveObj = geoserve.getJSONObject(i);
                        JSONObject contentObj = geoserveObj.getJSONObject("contents");
                        JSONObject geoJsonObj = contentObj.getJSONObject("geoserve.json");
                        detailsUrl = geoJsonObj.getString("url");
                    }
                    // Log.d("URL", detailsUrl);
                    getMoreDitails(detailsUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    public void getMoreDitails(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialogBuilder = new AlertDialog.Builder(MapsActivity.this);
                View view = getLayoutInflater().inflate(R.layout.popup, null);
                Button dissmissButton = (Button) view.findViewById(R.id.dismissPop);
                Button dissmissButtonTop = (Button) view.findViewById(R.id.desmissPopTop);
                TextView popList = (TextView) view.findViewById(R.id.popList);
                WebView htmlPop = (WebView) view.findViewById(R.id.htmlWebview);

                StringBuilder stringBuilder = new StringBuilder();

                try {
                    if(response.has("tectonicSummary") && response.getString("tectonicSummary") != null) {
                        JSONObject tectonic = response.getJSONObject("tectonicSummary");
                        if(tectonic.has("text") && tectonic.getString("text") != null) {
                            String text = tectonic.getString("text");
                            htmlPop.loadDataWithBaseURL(null, text, "text/html", "UTF-8", null);
                            Log.d("HTML", text);
                        }
                    }
                    JSONArray cities = response.getJSONArray("cities");
                    for(int i = 0; i < cities.length(); i++) {
                        JSONObject citiesObj = cities.getJSONObject(i);
                        stringBuilder.append(
                                "City: " + citiesObj.getString("name") + "\n" +
                                "Distance: " + citiesObj.getString("distance") + "\n" +
                                "Pubpualation: " +  citiesObj.getString("population"));
                        stringBuilder.append("\n\n");
                    }
                    popList.setText(stringBuilder);
                    // предоставляется 2 варианта закрытия окна
                    dissmissButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dissmissButtonTop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialogBuilder.setView(view);
                    dialog = dialogBuilder.create();
                    dialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}

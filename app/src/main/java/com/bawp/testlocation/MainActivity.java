package com.bawp.testlocation;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final int ALL_PERMISSIONS_RESULT = 1111;
    // позволяет управлять сервисами Google Play, управляя запросами
    private GoogleApiClient client;
    // обьеденный провайдер местоположения, для получения последнего известного местоположения устройства
    private FusedLocationProviderClient fusedLocationProviderClient;
    // создаем массив разрешений на запрос, для того чтобы убедится что пользователь имеет разрешение на данный запрос
    private ArrayList<String> permissionsToRequest;
    // разрешения в доступе
    private ArrayList<String> permissions = new ArrayList<>();
    // отказы в доступе
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private TextView locationTextView;
    private LocationRequest locationRequest;
    public static final long UPDATE_INTERVAL = 5000;
    public static final long FASTEST_INTERVAL = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // получает последнее известное положение устройства
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        locationTextView = findViewById(R.id.location_text_view);

        // добавляем необходимые разрешения, необходимые для запроса местоположения пользователя
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION); // позволяет определять местоположение с максимально возможной точностью, учитывает GPS и WIFI
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION); // приблизительная точность, основанная на определении по wifi.

        permissionsToRequest = permissionsToRequest(permissions);

        client = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                // добавляет слушатель события регистрации и сбоя подключения к клиенту
                .addOnConnectionFailedListener(this)
                // прослушивает все переопределенные методы
                .addConnectionCallbacks(this)
                .build();

       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    // создает список разрешений запрашиваемых пользователем
    private ArrayList<String> permissionsToRequest(ArrayList<String> wantendPermissions) {
        ArrayList<String> result = new ArrayList<>();
        // проверяем, было ли разрешение уже запрошено и принято
        for(String perm : wantendPermissions) {
            if(!hasPermission(perm)) {
                result.add(perm); // если пользователь дал разрешение
            }
        }
        return result;
    }

    private boolean hasPermission(String perm) {
        // Ecли версия SDK выше либо равна версии marshmellow
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // возращает true/false если данному пакет было предоставлено разрешение
            // checkSelfPermission() - определяет было ли предоставлено данному пакету разрешение
            return checkSelfPermission(perm) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(client != null) {
            client.connect(); // соединяется с сервисом GooglePlay
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(client != null) {
            client.disconnect(); // отсоединяется от сервиса GooglePlay
        }
    }


    @Override
    // проверяеем работу Google Play
    protected void onPostResume() {
        super.onPostResume();
        checkPlayServices();
    }

    public void checkPlayServices() {
        // проверка, доступен ли нам Google плей
        int errorCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        // если Google Плей не доступен
        if(errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = GoogleApiAvailability.getInstance().getErrorDialog(this, errorCode, errorCode, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Toast.makeText(MainActivity.this,"No servieces", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
            errorDialog.show();
        } else {
            Toast.makeText(MainActivity.this,"All is good!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(client != null && client.isConnected()) {
            // чтобы остановить обновления местоположения, вызовите removeLocationUpdates(), передав ему LocationCallback
            LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(new LocationCallback() {});
            client.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // проверка на включение разрешений
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // вызов слушателя при успешном завершении задачи
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Получаем последнее местоположение, однако оном может быть равнo null
                if(location != null) {
                    locationTextView.setText(MessageFormat.format("Lat: {0} Lon: {1}", location.getLatitude(), location.getLongitude()));
                }
            }
        });
        // обновление расположения в случае его изменения
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        // обеспечивает максимально точное местоположение
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL); // интервал запросов на обновление
        locationRequest.setFastestInterval(FASTEST_INTERVAL); // устанавливает наиболее быстрый интервал
        // проверка на разрешение
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You need to enable pemissions to display location!", Toast.LENGTH_LONG).show();
        }

        LocationServices.getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            // изменение локали
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if(locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    locationTextView.setText(MessageFormat.format("Lat: {0} Lon: {1}", location.getLatitude(), location.getLongitude()));
                }
            }
            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }
        }, null);
    }

    @Override
    // здесь отслеживается ответ на предоставление разрешения пользователю
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for(String perm : permissionsToRequest) {
                    // добавляем, если разрешение было отклонено
                    if(!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }
            if(permissionsRejected.size() > 0) {
                // проверка весии сборки
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // позволяет создать интерфейс для отображения дополнительного контеста для пользователя
                    // в случае если он отказал в предоставлении разрешения
                    if(shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                        // создаем диалоговое окно
                        new AlertDialog.Builder(MainActivity.this).setMessage("These permissions are mandatory to get location").
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            // запрашивает коллекцию разрешений которые будут предоставлены данной учетной записи
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    }
                                }).setNegativeButton("Сancel", null).create().show();
                    };
                }
            } else {
                if(client != null) {
                    client.connect();
                }
            }
        break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

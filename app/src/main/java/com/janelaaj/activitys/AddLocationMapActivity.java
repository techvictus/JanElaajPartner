package com.janelaaj.activitys;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.janelaaj.R;
import com.janelaaj.runtimepermission.PermissionResultCallback;
import com.janelaaj.runtimepermission.PermissionUtils;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.GPSTracker;
import com.janelaaj.utilities.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created On 18-05-2018
 *
 * @author Arshil Khan.
 */




public class AddLocationMapActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {

    private GoogleMap mMap;

    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    private int REQUEST_CODE_GPS_PERMISSIONS = 2;
    GPSTracker gps;
    Marker marker;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_location_map);

        setupToolbar();


        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_android:
                        Intent intent = new Intent(AddLocationMapActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;

            }
        });


        runTimePermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setupToolbar() {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    private void init() {


        gps = new GPSTracker(AddLocationMapActivity.this);
        if (gps != null) {
            Location location = gps.getLocation();
            double curlat = location.getLatitude();
            double curlon = location.getLongitude();
            LatLng currentpos = new LatLng(curlat, curlon);
            Log.d(Contants.LOG_TAG, "Location lat  " + curlat + " Location Long " + curlon);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(curlat, curlon), 10));

            marker = mMap.addMarker(new MarkerOptions().position(currentpos)
                    .title("Select your Location")
                    .snippet("Long press and move the marker to your location.")
                    .draggable(true));
            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_start)));
            //mMap.addMarker(new MarkerOptions().position(currentpos).title("Marker in Sydney"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentpos));
            try {
                String address = Utility.getLocationAddress(location, AddLocationMapActivity.this, "");
                Toast.makeText(AddLocationMapActivity.this, address, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("Marker", "Dragging");
            }

            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                LatLng markerLocation = marker.getPosition();
                // Toast.makeText(AddLocationMapActivity.this, markerLocation.toString(), Toast.LENGTH_LONG).show();
                Log.d("Marker", "finished");
                String address = null;
                try {
                    address = Utility.getLocationAddressThroughLatLong(markerLocation.latitude, markerLocation.longitude, AddLocationMapActivity.this, "");
                    Toast.makeText(AddLocationMapActivity.this, address, Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("Marker", "Started");

            }
        });
        TextView addLocationscreen = this.findViewById(R.id.addLocationscreen);
        addLocationscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddLocationMapActivity.this, AddLocationScreen.class);
                startActivity(intent);
            }
        });

        final EditText mobileNoedittextView = this.findViewById(R.id.mobileNoedittextView);
        ImageButton getlocationButton = this.findViewById(R.id.getlocationButton);
        getlocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = mobileNoedittextView.getText().toString();
                if (address != null) {
                    LatLng addLatlng = getLocationFromAddress(address);
                    if (addLatlng != null) {
                        mMap.clear();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addLatlng, 10));

                        marker = mMap.addMarker(new MarkerOptions().position(addLatlng)
                                .title("Your Location")
                                .snippet(address)
                                .draggable(true));
                    }
                } else {
                    Toast.makeText(AddLocationMapActivity.this, "Please enter Address!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(AddLocationMapActivity.this);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            if (address != null) {
                Address location = address.get(0);
                p1 = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        // mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        init();

    }

    //----------------------------------------------------------------------------------
    private void runTimePermission() {
        permissionUtils = new PermissionUtils(AddLocationMapActivity.this);

        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(android.Manifest.permission.MODIFY_AUDIO_SETTINGS);
       /* permissions.add(android.Manifest.permission.CALL_PHONE);
        permissions.add(android.Manifest.permission.READ_PHONE_STATE);
        permissions.add(android.Manifest.permission.READ_CALL_LOG);
        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.CAMERA);
        permissions.add(android.Manifest.permission.WAKE_LOCK);
        permissions.add(Manifest.permission.SEND_SMS);
        permissions.add(Manifest.permission.ACCESS_NOTIFICATION_POLICY);*/


        permissionUtils.check_permission(permissions, "Location, Phone and Storage Services Permissions are required for this App.", REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
    }

    @Override
    public void PermissionGranted(int request_code) {
        checkGpsEnable();
        //startLocationAlarmService();
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
        finish();
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION", "DENIED");
        finish();
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
        neverAskAgainAlert();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            permissionUtils.check_permission(permissions, "Location, Phone and Storage Services Permissions are required for this App.", REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
        if (requestCode == REQUEST_CODE_GPS_PERMISSIONS) {
            checkGpsEnable();
        }
    }

    private void neverAskAgainAlert() {
        //Previously Permission Request was cancelled with 'Dont Ask Again',
        // Redirect to Settings after showing Information about why you need the permission
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddLocationMapActivity.this);
        builder.setTitle("Need Multiple Permissions");
        builder.setCancelable(false);
        builder.setMessage("Location, Phone and Storage Services Permissions are required for this App.");
        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        builder.show();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_CODE_GPS_PERMISSIONS);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        checkGpsEnable();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    //    check gps enable in device or not
    private void checkGpsEnable() {
        try {
            boolean isGPSEnabled = false;
            boolean isNetworkEnabled = false;
            final LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                buildAlertMessageNoGps();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

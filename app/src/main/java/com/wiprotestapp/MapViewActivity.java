package com.wiprotestapp;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by admin on 27-Jul-16.
 */
public class MapViewActivity extends FragmentActivity {

    private GoogleMap map;
    private float mLatitude;
    private float mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view_activity);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            mLatitude= bundle.getFloat("lat");
            mLongitude= bundle.getFloat("long");
        }

        //replace GOOGLE MAP fragment in this Activity
        replaceMapFragment();
    }

    private void replaceMapFragment() {
        SupportMapFragment fm = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
        GoogleMap map = fm.getMap();
        // Enable Zoom
        map.getUiSettings().setZoomGesturesEnabled(true);
        //set Map TYPE
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //enable Current location Button
        map.setMyLocationEnabled(true);


        LatLng loc = new LatLng(mLatitude, mLongitude);
        Marker marker;
        marker = map.addMarker(new MarkerOptions().position(loc));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
        //set "listener" for changing my location
        //map.setOnMyLocationChangeListener(myLocationChangeListener());
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener() {
        return new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                Toast.makeText(MapViewActivity.this,"latitude==="+latitude,Toast.LENGTH_SHORT).show();

                Marker marker;
                marker = map.addMarker(new MarkerOptions().position(loc));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                //locationText.setText("You are at [" + longitude + " ; " + latitude + " ]");

                //get current address by invoke an AsyncTask object
               // new GetAddressTask(LocationActivity.this).execute(String.valueOf(latitude), String.valueOf(longitude));
            }
        };
    }

    public void callBackDataFromAsyncTask(String address) {
        //addressText.setText(address);
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view_activity);
    }*/
}


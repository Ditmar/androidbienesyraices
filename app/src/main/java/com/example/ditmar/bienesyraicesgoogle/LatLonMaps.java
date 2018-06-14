package com.example.ditmar.bienesyraicesgoogle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.example.ditmar.bienesyraicesgoogle.DATA.DataApp;
import com.example.ditmar.bienesyraicesgoogle.DATA.UserData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LatLonMaps extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap mMap;
    private MarkerOptions marker;
    private Context root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = this;
        //marker = new ArrayList<MarkerOptions>();
        setContentView(R.layout.activity_lat_lon_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MapView map = this.findViewById(R.id.mapView);
        if (map != null) {
            map.onCreate(null);
            map.onResume();
            // Set the map ready callback to receive the GoogleMap object
            map.getMapAsync(this);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (marker != null) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.patch(DataApp.REST_HOME_PATCH + UserData.ID, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Intent camera = new Intent(root, CameraForm.class);
                            root.startActivity(camera);
                            //AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
                        }
                    });
                }
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng potosi = new LatLng(-19.578297, -65.758633);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(potosi, 14));
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {

        mMap.clear();
        marker = new MarkerOptions();
        marker.position(latLng);
        marker.title("Homes");
        marker.draggable(true);
        mMap.addMarker(marker);
    }


}

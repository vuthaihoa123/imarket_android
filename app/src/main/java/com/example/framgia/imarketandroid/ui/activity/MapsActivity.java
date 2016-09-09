package com.example.framgia.imarketandroid.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.model.CustomMarker;
import com.example.framgia.imarketandroid.ui.fragments.CategoryStallFragment;
import com.example.framgia.imarketandroid.ui.views.CustomMarkerView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap
    .OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private List<CustomMarker> mCustomMarkers = new ArrayList<>();
    private HashMap<Marker, CustomMarker> mMarkerPointHashMap = new HashMap<>();

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
        setGroundOverlay();
        mMap.setOnMarkerClickListener(this);
        init();
    }

    private void init() {
        mCustomMarkers = FakeContainer.getCustomMarker();
        for (CustomMarker custom : mCustomMarkers) {
            drawMarker(custom);
        }
        mMap.setInfoWindowAdapter(new MarkerInfoAdapter());
        mMap.setOnInfoWindowClickListener(this);
    }

    private void setGroundOverlay() {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.mbt2);
        LatLng testLat = FakeContainer.sLatLng;
        GroundOverlayOptions goo = new GroundOverlayOptions()
                .image(bitmapDescriptor)
                .position(testLat, FakeContainer.sGroundFirstParameter, FakeContainer.sGroundSecondParameter)
                .bearing(FakeContainer.sGroundThirdParameter);
      mMap.addGroundOverlay(goo);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(testLat)
                .zoom(FakeContainer.ZOOM_RANGE)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(testLat));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void drawMarker(CustomMarker marker) {
        View markerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.item_marker, null);
        CustomMarkerView customMarkerView = (CustomMarkerView) markerView.findViewById(R.id.custom_marker_view);
        customMarkerView.setPercentValue(marker.getNumber());
        customMarkerView.setTextforMarker();
        switch (marker.getCategory().getId()) {
            case FakeContainer.STORE_TYPE_1:

                break;
            case FakeContainer.STORE_TYPE_2:
                customMarkerView.setBackground(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu_gallery, null));
                break;
        }
        LatLng newLatLng = new LatLng(marker.getLatitude(), marker.getLongitude());
        final Marker currentMarker = mMap.addMarker(new MarkerOptions().position(newLatLng)
                .icon(BitmapDescriptorFactory.fromBitmap(createBitmapFromView(this, markerView))));
        mMarkerPointHashMap.put(currentMarker, marker);
    }

    private Bitmap createBitmapFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new DrawerLayout.LayoutParams(
                DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        CustomMarker customMarker = mMarkerPointHashMap.get(marker);
        builder.include(customMarker.getPosition());
        // Get the LatLngBounds
        final LatLngBounds bounds = builder.build();
        // Animate camera to the bounds
        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, FakeContainer.CAMERA_PARAMETER));
        } catch (Exception e) {
            e.printStackTrace();
        }
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        CustomMarker customMarker = mMarkerPointHashMap.get(marker);
        startActivity(new Intent(MapsActivity.this, CategoryStallFragment.class));
    }

    public class MarkerInfoAdapter implements GoogleMap.InfoWindowAdapter {

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View v = getLayoutInflater().inflate(R.layout.item_marker_information, null);
            TextView textView = (TextView) v.findViewById(R.id.text_marker);
            CustomMarker customMarker = mMarkerPointHashMap.get(marker);
            textView.setText(FakeContainer.ID_PRODUCT + customMarker.getId());
            return v;
        }
    }
}

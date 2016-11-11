package com.example.framgia.imarketandroid.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.util.MapUntils;
import com.example.framgia.imarketandroid.util.findpath.ConnectionDetector;
import com.example.framgia.imarketandroid.util.findpath.DirectionsJSONParser;
import com.example.framgia.imarketandroid.util.findpath.InternetUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionToMarket extends FragmentActivity
    implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener {
    private AlertDialog mAlertDialog;
    private boolean mIsConnect;
    private LatLng mOrigin;
    private LatLng mSource = new LatLng(21.027498, 105.899202);
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;
    private SupportMapFragment mFragment;
    private Marker mCurrLocationMarker;
    private Location mLocation;
    private ImageButton mReLoad;
    private LocationManager mLocationManager;
    private String mStr_origin= "origin=";
    private String mStr_dest= "destination=";
    private String mSensor= "sensor=false";
    private String mHeadJson= "https://maps.googleapis.com/maps/api/directions/";
    private String mOutput = "json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_to_market);
        mLocationManager = (LocationManager) getSystemService(Context
            .LOCATION_SERVICE);
        mFragment =
            (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_direction);
        mFragment.getMapAsync(this);
        mReLoad = (ImageButton) findViewById(R.id.refresh_find_path);
        mReLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
        mIsConnect = InternetUtil.isInternetConnected(this);
        mAlertDialog = new AlertDialog.Builder(DirectionToMarket.this).create();
    }

    private void drawPathToMarker(Location location) {
        if (mIsConnect) {
            if (mLocationManager.isProviderEnabled(mLocationManager.GPS_PROVIDER)) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location
                    .getLatitude(), location.getLongitude()), 14));
                CameraPosition cameraPosition =
                    new CameraPosition.Builder().target(mSource).bearing(90).zoom(15).tilt(30)
                        .build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mOrigin = mSource;
                //crete option for origin
                final MarkerOptions options = new MarkerOptions();
                options.title(String.valueOf(R.string.start_point));
                options.snippet(String.valueOf(R.string.your_location));
                options.position(new LatLng(location.getLatitude(), location.getLongitude()));
                final MarkerOptions optionsTarget = new MarkerOptions();
                optionsTarget.title(String.valueOf(R.string.target));
                optionsTarget.snippet(String.valueOf(R.string.market_name_text));
                optionsTarget.position(mSource);
                // create option for target
                Marker marker, markerTarget;
                markerTarget = mGoogleMap.addMarker(optionsTarget);
                marker = mGoogleMap.addMarker(options);
                marker.showInfoWindow();
                String url = getDirectionsUrl(mOrigin, mSource);
                // Sensor enabled
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.execute(url);
                mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        Toast.makeText(getApplicationContext(), String.valueOf(R.string.ok),
                            Toast.LENGTH_LONG).show();
                    }
                });
                // Getting reference to SupportMapFragment of the activity_main
            } else {
            }
        } else {
            mAlertDialog.setTitle(String.valueOf(R.string.problem_net_work));
            mAlertDialog.setMessage(String.valueOf(R.string.check_net_work));
            mAlertDialog
                .setButton(String.valueOf(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //getActivity().finish();
                    }
                });
            mAlertDialog.show();
        }
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        mGoogleMap = gMap;
        checkPermission();
        mGoogleMap.setMyLocationEnabled(true);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
            mGoogleApiClient);
        mLocation = mLastLocation;
        drawPathToMarker(mLocation);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            mOrigin = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(mOrigin);
            markerOptions.title(String.valueOf(R.string.current_location));
            markerOptions
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter
        LocationServices.FusedLocationApi
            .requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        //place marker at current position
        //mGoogleMap.clear();
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        mLocation = location;
        mOrigin = new LatLng(location.getLatitude(), location.getLongitude());
        //If you only need one location, unregister the listener
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    //    private void buildAlertMessageNoGps() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
//            .setCancelable(false)
//            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                }
//            })
//            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                    dialog.cancel();
//                }
//            });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }
    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
            StringBuilder str_origin =new StringBuilder(mStr_origin);
            str_origin.append(origin.latitude );
            str_origin.append(","  );
            str_origin.append(origin.longitude );
        // Destination of route
        StringBuilder str_dest= new StringBuilder(mStr_dest);
        str_dest.append(dest.latitude);
        str_dest.append("," );
        str_dest.append(dest.longitude);
        // Sensor enabled
        String sensor = mSensor;
        // Building the parameters to the web service
        // Building thdrawe parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        // Output format

        String url = mHeadJson + mOutput + "?" + parameters;
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
            // For storing data from web service
            String data = "";
            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null) {
                Toast.makeText(DirectionToMarket.this, R.string.current_location_warning, Toast
                    .LENGTH_LONG).show();
            } else {
                ParserTask parserTask = new ParserTask();
                // Invokes the thread for parsing the JSON data
                parserTask.execute(result);
            }
        }
    }

    private class ParserTask
        extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            if (result == null)
                return;
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.RED);
            }
            // Drawing polyline in the Google Map for the i-th route
            mGoogleMap.addPolyline(lineOptions);
        }
    }
}

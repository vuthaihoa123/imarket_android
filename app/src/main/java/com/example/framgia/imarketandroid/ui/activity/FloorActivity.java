package com.example.framgia.imarketandroid.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.model.CustomMarker;
import com.example.framgia.imarketandroid.data.model.Edge;
import com.example.framgia.imarketandroid.data.model.Floor;
import com.example.framgia.imarketandroid.data.model.Graph;
import com.example.framgia.imarketandroid.data.model.Point;
import com.example.framgia.imarketandroid.data.model.Shop;
import com.example.framgia.imarketandroid.data.remote.RealmRemote;
import com.example.framgia.imarketandroid.ui.views.CustomMarkerView;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.algorithm.DijkstraAlgorithm;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by toannguyen201194 on 19/07/2016.
 */
public class FloorActivity extends AppCompatActivity implements AdapterView
    .OnItemSelectedListener, GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback, View
    .OnClickListener {
    public static int sResumeValue = 0;
    private GoogleMap mMap;
    private Spinner mSpinerFloor, mSpinnerProduct;
    private Button mBtnControl, mBtnDelete, mBtnDraw, mBtnGetPath;
    private Button mBtnDoneLocation, mBtnCancelLocation;
    private Dialog mDialog;
    private LinearLayout mLayoutLocation;
    private Switch mSwitchLocation;
    private List<Marker> mListMarker = new ArrayList<>();
    private List<Shop> mShopList = new ArrayList<>();
    private List<Floor> mFloorList = new ArrayList<>();
    private List<Edge> mEdges;
    private List<Point> mNodes = null;
    private RealmList<Point> mNodesDisplay = null;
    private RealmList<Point> mVertexesHoa = new RealmList<>();
    private RealmList<Edge> mEdgesHoa = new RealmList<>();
    private ArrayAdapter<Shop> mAdapterShop;
    private ArrayAdapter<Floor> mAdapterFloor;
    private Polyline mline;
    private int mIndex, mIndexStore;
    private boolean mCheckZoom = false;
    private boolean mCheckSwitch = false;
    private boolean mCheckCurrentLocation = false;
    private Point mCurrentLocation;
    private Point mTargetLocation;
    private int mFlagOne = 1;
    private int mFlagTWO = 2;
    private int mFlagThree = 3;
    private LatLng mTempLatLng;
    private List<CustomMarker> mCustomMarkers = new ArrayList<>();
    private HashMap<Marker, CustomMarker> mMarkerPointHashMap = new HashMap<>();
    private Collection<Marker> mListcustomMarker = new ArrayList<>();
    private LatLng mAeon = new LatLng(21.026975, 105.899302);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_floor);
        mShopList = FakeContainer.initListShop();
        initMap();
        hideStatusBar();
        initViews();
        createDataForFloor();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mNodes = RealmRemote.getAllPoint();
        mNodesDisplay = RealmRemote.getListPointDisplay(0);
        mEdges = RealmRemote.getListEdge();
        mBtnControl = (Button) findViewById(R.id.btn_show_dialog);
        mBtnDelete = (Button) findViewById(R.id.btn_delete_data);
        mBtnDraw = (Button) findViewById(R.id.btn_find_path);
        mBtnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorActivity.sResumeValue = 2;
                Intent intent = new Intent(FloorActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });
        mBtnGetPath = (Button) findViewById(R.id.btn_demo_find_way);
        mBtnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorActivity.sResumeValue = 1;
                Intent intent = new Intent(FloorActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        mSwitchLocation = (Switch) findViewById(R.id.switch1);
        mSwitchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSwitchLocation.setText(R.string.find_store);
                    mCheckSwitch = true;
                } else {
                    mSwitchLocation.setText(R.string.check_store);
                    mCheckSwitch = false;
                }
            }
        });
        mSpinerFloor = (Spinner) findViewById(R.id.spinner_choose_floor);
        mSpinnerProduct = (Spinner) findViewById(R.id.spinner_choose_product);
        mAdapterFloor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mFloorList);
        mAdapterFloor.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSpinerFloor.setAdapter(mAdapterFloor);
        mAdapterShop = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mShopList);
        mAdapterShop.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSpinnerProduct.setAdapter(mAdapterShop);
        mSpinerFloor.setOnItemSelectedListener(this);
        mSpinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setCustomMarkers(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        mLayoutLocation = (LinearLayout) findViewById(R.id.layout_location);
        mLayoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDiaLogLocation();
            }
        });
    }

    private void setDiaLogLocation() {
        mDialog = new Dialog(FloorActivity.this);
        mDialog.setContentView(R.layout.custom_dialog);
        mDialog.setTitle(R.string.current_location_title);
        final EditText edtLocation = (EditText) mDialog.findViewById(R.id.edtLocation);
        mBtnDoneLocation = (Button) mDialog.findViewById(R.id.done_location);
        mBtnDoneLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtLocation.getText().length() > 0) {
                    String mLocation = edtLocation.getText().toString().toUpperCase();
                    mDialog.dismiss();
                    LatLng currentLatLng = null;
                    currentLatLng = RealmRemote.getLocationFromName(mLocation);
                    mCurrentLocation = RealmRemote.getObjectPointFromName(mLocation);
                    if (currentLatLng != null) {
                        mCheckCurrentLocation = true;
                        for (int i = 0; i < mListMarker.size(); i++) {
                            mListMarker.get(i).showInfoWindow();
                        }
                        Marker marker = mMap.addMarker(new MarkerOptions().position(currentLatLng)
                            .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                        marker.showInfoWindow();
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
                    } else
                        Toast.makeText(FloorActivity.this, R.string.warning_location,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        mBtnCancelLocation = (Button) mDialog.findViewById(R.id.cancel_location);
        mBtnCancelLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        edtLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    mBtnDoneLocation.setEnabled(true);
                } else
                    mBtnDoneLocation.setEnabled(false);
            }
        });
        mDialog.show();
    }

    private void createDataForFloor() {
        Floor floor = new Floor(0, getString(R.string.choose_floor));
        Floor floor1 = new Floor(1, getString(R.string.first_floor));
        Floor floor2 = new Floor(2, getString(R.string.second_floor));
        mFloorList.add(floor);
        mFloorList.add(floor1);
        mFloorList.add(floor2);
        mAdapterFloor.notifyDataSetChanged();
    }

    private void hideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }

    private void loadListShop(Floor floor) {
        mShopList.clear();
        mShopList.addAll(floor.getShopList());
        mAdapterShop.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        moveCamera();
        setCustomMarkers(0);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setInfoWindowAdapter(new MarkerInfoAdapter());
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (mCheckSwitch == true) {
                    if (mCheckCurrentLocation == true) {
                        mTargetLocation = RealmRemote.getObjectPointFromName(marker.getTitle());
                        setDrawPath();
                    }
                } else {
                    LatLngBounds.Builder builder = LatLngBounds.builder();
                    CustomMarker customMarker = mMarkerPointHashMap.get(marker);
                    builder.include(customMarker.getPosition());
                    final LatLngBounds bounds = builder.build();
                    try {
                        mMap.animateCamera(CameraUpdateFactory
                            .newLatLngBounds(bounds, FakeContainer.CAMERA_PARAMETER));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    marker.showInfoWindow();
                }
                return true;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                FloorActivity.sResumeValue = mFlagThree;
                mTempLatLng = latLng;
                Intent intent = new Intent(FloorActivity.this, ChooseStoreType.class);
                startActivity(intent);
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                double lat = latLng.latitude;
                double lng = latLng.longitude;
                SharedPreferences preferences =
                    getSharedPreferences(getString(R.string.share_point), MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                mIndexStore = preferences.getInt(getString(R.string.idPoint), 0);
                mIndexStore++;
                editor.putInt(getString(R.string.idPoint), mIndexStore);
                editor.commit();
                Point mPoint =
                    new Point(mIndexStore, lat, lng, 1, getString(R.string.C) + mIndexStore);
                RealmRemote.savePoint(mPoint);
                Marker locationMarket = mMap.addMarker(
                    new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude))
                        .title(mPoint.getName()));
                locationMarket.showInfoWindow();
            }
        });
    }

    private void setGroundOverlay() {
        BitmapDescriptor BD = BitmapDescriptorFactory.fromResource(R.drawable.picture_aeon);
        LatLngBounds newarkBounds = new LatLngBounds(
            new LatLng(21.025933, 105.896914),
            new LatLng(21.028660, 105.901372));
        GroundOverlayOptions goo =
            new GroundOverlayOptions().image(BD).positionFromBounds(newarkBounds)
                .bearing((float) 26.3248);
        GroundOverlay imageOverlay = mMap.addGroundOverlay(goo);
        mMap.addMarker(
            new MarkerOptions().position(mAeon).title(getString(R.string.name_commerce)));
    }

    private void moveCamera() {
        setGroundOverlay();
        CameraPosition cameraPosition =
            new CameraPosition.Builder().target(mAeon).zoom((float) 19.5).bearing((float) -36.8)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mAeon));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                float zoom = cameraPosition.zoom;
                if (zoom > 20 && mCheckZoom == false) {
                    setMarkerVisible(true);
                    mCheckZoom = true;
                } else if (zoom < 20 && mCheckZoom == true) {
                    setMarkerVisible(false);
                    mCheckZoom = false;
                }
            }
        });
    }

    private void setMarkerVisible(boolean check) {
        for (Marker marker : mListcustomMarker) {
            marker.setVisible(check);
        }
    }

    private void setListEdge() {
        RealmResults<Edge> edges = RealmRemote.getListEdgeDisplay();
        edges.size();
        for (int i = 0; i < edges.size(); i++) {
            mMap.addPolyline(new PolylineOptions()
                .add(RealmRemote.getLocationFromName(edges.get(i).getNameStart()),
                    RealmRemote.getLocationFromName(edges.get(i).getNameEnd())).width(2));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FloorActivity.sResumeValue == mFlagOne) {
            LatLng first = RealmRemote.getLocationFromName(DialogActivity.sFirstPoint);
            LatLng second;
            float results[] = new float[1];
            String[] tempListPoint = DialogActivity.sSecondPoint.split(getString(R.string.comma));
            for (int i = 0; i < tempListPoint.length; i++) {
                second = RealmRemote.getLocationFromName(tempListPoint[i]);
                Location
                    .distanceBetween(first.latitude, first.longitude, second.latitude,
                        second.longitude,
                        results);
                RealmRemote.saveEdge(
                    new Edge(DialogActivity.sFirstPoint, tempListPoint[i], results[0]));
                RealmRemote.saveEdge(
                    new Edge(tempListPoint[i], DialogActivity.sFirstPoint, results[0]));
                Polyline polyline =
                    mMap.addPolyline(new PolylineOptions().add(first, second).width(2));
                FloorActivity.sResumeValue = 0;
            }
        }
        if (FloorActivity.sResumeValue == mFlagTWO) {
            setDrawPath();
            FloorActivity.sResumeValue = 0;
        }
        if (FloorActivity.sResumeValue == mFlagThree) {
            SharedPreferences preferences =
                getSharedPreferences(getString(R.string.share_point), MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            mIndex = preferences.getInt(getString(R.string.idPoint), 0);
            mIndex++;
            editor.putInt(getString(R.string.idPoint), mIndex);
            editor.commit();
            Point mPoint = new Point(mIndex, mTempLatLng.latitude, mTempLatLng
                .longitude, ChooseStoreType.sAvatar + 1, getString(R
                .string.M) + mIndex);
            RealmRemote.savePoint(mPoint);
            drawMarker(RealmRemote.createCustomMarkerFromPoint(mPoint));
            FloorActivity.sResumeValue = 0;
        }
    }

    public void setDrawPath() {
        if (mline != null)
            mline.remove();
        mVertexesHoa.clear();
        for (int i = 0; i < mNodes.size(); i++) {
            Point p = mNodes.get(i);
            mVertexesHoa.add(p);
        }
        mEdgesHoa.clear();
        for (int i = 0; i < mEdges.size(); i++) {
            Edge e = mEdges.get(i);
            mEdgesHoa.add(e);
        }
        Graph graph = new Graph(mVertexesHoa, mEdgesHoa);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph, this);
        Point vertexF = mCurrentLocation;
        dijkstra.execute(vertexF);
        Point vertex1 = mTargetLocation;
        LinkedList<Point> path = dijkstra.getPath(vertex1);
        if (path != null)
            for (int i = 0; i < path.size() - 1; i++) {
                LatLng src = RealmRemote.getLocationFromName(path.get(i).getName());
                LatLng dest = RealmRemote.getLocationFromName(path.get(i + 1).getName());
                mline = mMap.addPolyline(
                    new PolylineOptions().add(
                        src, dest).width(2).color(Color.BLUE).geodesic(true)
                );
            }
        else
            Toast.makeText(FloorActivity.this, R.string.current_location_warning + mEdges.size
                (), Toast.LENGTH_LONG)
                .show();
        FloorActivity.sResumeValue = 0;
    }

    private void drawMarker(CustomMarker marker) {
        View markerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
            .inflate(R.layout.item_marker, null);
        CustomMarkerView customMarkerView =
            (CustomMarkerView) markerView.findViewById(R.id.custom_marker_view);
        customMarkerView.setPercentValue(marker.getNumber());
        customMarkerView.setTextforMarker();
        switch (marker.getCategory().getId()) {
            case FakeContainer.STORE_TYPE_1:
                customMarkerView.setBackground(
                    ResourcesCompat.getDrawable(getResources(), Constants.LIST_AVATAR_STORE[0],
                        null));
                break;
            case FakeContainer.STORE_TYPE_2:
                customMarkerView.setBackground(
                    ResourcesCompat.getDrawable(getResources(), Constants.LIST_AVATAR_STORE[1],
                        null));
                break;
            case FakeContainer.STORE_TYPE_3:
                customMarkerView.setBackground(
                    ResourcesCompat.getDrawable(getResources(), Constants.LIST_AVATAR_STORE[2],
                        null));
                break;
            case FakeContainer.STORE_TYPE_4:
                customMarkerView.setBackground(
                    ResourcesCompat.getDrawable(getResources(), Constants.LIST_AVATAR_STORE[3],
                        null));
                break;
            case FakeContainer.STORE_TYPE_5:
                customMarkerView.setBackground(
                    ResourcesCompat.getDrawable(getResources(), Constants.LIST_AVATAR_STORE[4],
                        null));
                break;
            case FakeContainer.STORE_TYPE_6:
                customMarkerView.setBackground(
                    ResourcesCompat.getDrawable(getResources(), Constants.LIST_AVATAR_STORE[5],
                        null));
                break;
        }
        customMarkerView.setOnClickListener(this);
        LatLng newLatLng = new LatLng(marker.getLatitude(), marker.getLongitude());
        final Marker currentMarker = mMap.addMarker(new MarkerOptions().position(newLatLng)
            .icon(BitmapDescriptorFactory.fromBitmap(createBitmapFromView(this, markerView))));
        mMarkerPointHashMap.put(currentMarker, marker);
    }

    public void setCustomMarkers(int type) {
        if (type != 0)
            setMarkerVisible(false);
        mNodesDisplay = RealmRemote.getListPointDisplay(type);
        for (Point point : mNodesDisplay) {
            drawMarker(RealmRemote.createCustomMarkerFromPoint(point));
        }
        mListcustomMarker = mMarkerPointHashMap.keySet();
    }

    private Bitmap createBitmapFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new DrawerLayout.LayoutParams(
            DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
            Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.custom_marker_view:
                Intent intent = new Intent(FloorActivity.this, ChooseStoreType.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        CustomMarker customMarker = mMarkerPointHashMap.get(marker);
        startActivity(new Intent(FloorActivity.this, HomeStoreActivity.class));
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
            String text = null;
            switch (customMarker.getCategory().getId()) {
                case FakeContainer.STORE_TYPE_1:
                    text =
                        String.valueOf(getString(R.string.food_store)) + " " + customMarker.getId();
                    break;
                case FakeContainer.STORE_TYPE_2:
                    text = String.valueOf(getString(R.string.fashion_name)) + " " + customMarker
                        .getId();
                    break;
                case FakeContainer.STORE_TYPE_3:
                    text = String.valueOf(getString(R.string.book_shop_name)) + " " + customMarker
                        .getId();
                    break;
                case FakeContainer.STORE_TYPE_4:
                    text = String.valueOf(getString(R.string.cosmetic_name)) + " " + customMarker
                        .getId();
                    break;
                case FakeContainer.STORE_TYPE_5:
                    text = String.valueOf(getString(R.string.movie_theater)) + " " + customMarker
                        .getId();
                    break;
                case FakeContainer.STORE_TYPE_6:
                    text = String.valueOf(getString(R.string.game_center_name)) + " " +
                        customMarker.getId();
                    break;
            }
            textView.setText(text);
            return v;
        }
    }
}
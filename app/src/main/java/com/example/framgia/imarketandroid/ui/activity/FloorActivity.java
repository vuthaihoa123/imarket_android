package com.example.framgia.imarketandroid.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Edge;
import com.example.framgia.imarketandroid.data.model.Floor;
import com.example.framgia.imarketandroid.data.model.Graph;
import com.example.framgia.imarketandroid.data.model.Point;
import com.example.framgia.imarketandroid.data.model.Shop;
import com.example.framgia.imarketandroid.data.remote.RealmRemote;
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
import java.util.LinkedList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by toannguyen201194 on 19/07/2016.
 */
public class FloorActivity extends AppCompatActivity implements AdapterView
        .OnItemSelectedListener, OnMapReadyCallback, View.OnClickListener {
    public static int sStatement = 0;
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
    private boolean mCheckSwitch = true;
    private boolean mCheckCurrentLocation = false;
    private Point mCurrentLocation;
    private Point mTargetLocation;
    private int mFlagOne=1;
    private int mFlagTWO=2;
    private EditText mEdtDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_floor);
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
        //  mNodesDisplay = RealmRemote.getListPointDisplay();
        mNodesDisplay = RealmRemote.getListPointDisplay();
        mEdges = RealmRemote.getListEdge();
        mBtnControl = (Button) findViewById(R.id.btn_show_dialog);
        mBtnDelete = (Button) findViewById(R.id.btn_delete_data);
        mBtnDraw = (Button) findViewById(R.id.btn_find_path);
        mBtnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorActivity.sStatement = 2;
                Intent intent = new Intent(FloorActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });
        mBtnGetPath = (Button) findViewById(R.id.btn_demo_find_way);
        mEdtDelete = (EditText) findViewById(R.id.edt_delete);
        mBtnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmRemote.deletePoint(mEdtDelete.getText().toString());
            }
        });
        mBtnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorActivity.sStatement = 1;
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
                        setListMarker();
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

    private void createDataForShop() {
        if (mSpinerFloor.getSelectedItemPosition() > 0) {
            Shop shop1 = new Shop(1, getString(R.string.clother));
            Shop shop2 = new Shop(2, getString(R.string.dress));
            mShopList.add(shop1);
            mShopList.add(shop2);
            Floor c = mFloorList.get(mSpinerFloor.getSelectedItemPosition());
            c.setShopList(mShopList);
            loadListShop(c);
        }
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
        if (i > 0) {
            createDataForShop();
        }
        loadListShop(mFloorList.get(i));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        moveCamera();
        setListMarker();
        //setListEdge();
        if (mEdges.size() > 0)
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (mCheckSwitch == true) {
                        if (mCheckCurrentLocation == true) {
                            mTargetLocation = RealmRemote.getObjectPointFromName(marker.getTitle());
                            setDrawPath();
                        } else
                            Toast.makeText(FloorActivity.this, R.string.current_location_title, Toast
                                    .LENGTH_LONG).show();
                    }
                    return false;
                }
            });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                double lat = latLng.latitude;
                double lng = latLng.longitude;
                SharedPreferences preferences = getSharedPreferences(getString(R.string.share_point), MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                mIndex = preferences.getInt(getString(R.string.idPoint), 0);
                mIndex++;
                editor.putInt(getString(R.string.idPoint), mIndex);
                editor.commit();
                Point mPoint = new Point(mIndex, lat, lng, 2, getString(R.string.M) + mIndex);
                RealmRemote.savePoint(mPoint);
                Marker locationMarket = mMap.addMarker(
                        new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude))
                                .title(mPoint.getName()));
                locationMarket.showInfoWindow();
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                double lat = latLng.latitude;
                double lng = latLng.longitude;
                SharedPreferences preferences = getSharedPreferences(getString(R.string.share_point), MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                mIndexStore = preferences.getInt(getString(R.string.idPoint), 0);
                mIndexStore++;
                editor.putInt(getString(R.string.idPoint), mIndexStore);
                editor.commit();
                Point mPoint = new Point(mIndexStore, lat, lng, 1, getString(R.string.C) + mIndexStore);
                RealmRemote.savePoint(mPoint);
                Marker locationMarket = mMap.addMarker(
                        new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude))
                                .title(mPoint.getName()));
                locationMarket.showInfoWindow();
            }
        });
    }

    private void moveCamera() {
        BitmapDescriptor BD = BitmapDescriptorFactory.fromResource(R.drawable.picture_aeon);
        LatLng bigC = new LatLng(21.026975, 105.899302);
        LatLngBounds newarkBounds = new LatLngBounds(
                new LatLng(21.025933, 105.896914),
                new LatLng(21.028660, 105.901372));
        GroundOverlayOptions goo =
                new GroundOverlayOptions().image(BD).positionFromBounds(newarkBounds)
                        .bearing((float) 26.3248);
        GroundOverlay imageOverlay = mMap.addGroundOverlay(goo);
        mMap.addMarker(new MarkerOptions().position(bigC).title(getString(R.string.name_commerce)));
        CameraPosition cameraPosition =
                new CameraPosition.Builder().target(bigC).zoom((float) 19.5).bearing((float) -36.8)
                        .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bigC));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                float zoom = cameraPosition.zoom;
                if (zoom > 20 && mCheckZoom == false) {
                    for (int i = 0; i < mListMarker.size(); i++) {
                        mListMarker.get(i).setVisible(true);
                    }
                    mCheckZoom = true;
                } else if (zoom < 20 && mCheckZoom == true) {
                    for (int i = 0; i < mListMarker.size(); i++) {
                        mListMarker.get(i).setVisible(false);
                    }
                    mCheckZoom = false;
                }
            }
        });
    }

    private void setListMarker() {
        for (int i = 0; i < mNodesDisplay.size(); i++) {
            MarkerOptions options = new MarkerOptions()
                    .position(RealmRemote.getLocationFromName(mNodesDisplay.get(i).getName()))
                    .title(mNodesDisplay.get(i).getName());
            Marker marker = mMap.addMarker(options);
            mListMarker.add(marker);
        }
    }

    private void setListEdge() {
        RealmResults<Edge> edges = RealmRemote.getListEdgeDisplay();
        edges.size();
        for (int i = 0; i < edges.size(); i++) {
            mMap.addPolyline(new PolylineOptions().add(RealmRemote.getLocationFromName(edges.get(i).getNameStart()), RealmRemote.getLocationFromName(edges.get(i).getNameEnd())).width(2));
        }
        Toast.makeText(FloorActivity.this, " " + RealmRemote.getLocationFromName(edges.get(0).getNameEnd()).latitude, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DialogActivity.sDraw == mFlagOne) {
            LatLng first = RealmRemote.getLocationFromName(DialogActivity.sFirstPoint);
            LatLng second;
            float results[] = new float[1];
            String[] tempListPoint = DialogActivity.sSecondPoint.split(getString(R.string.comma));
            for (int i = 0; i < tempListPoint.length; i++) {
                second = RealmRemote.getLocationFromName(tempListPoint[i]);
                Location
                        .distanceBetween(first.latitude, first.longitude, second.latitude, second.longitude,
                                results);
                RealmRemote.saveEdge(
                        new Edge(DialogActivity.sFirstPoint, tempListPoint[i], results[0]));
                RealmRemote.saveEdge(
                        new Edge(tempListPoint[i], DialogActivity.sFirstPoint, results[0]));
                Polyline polyline = mMap.addPolyline(new PolylineOptions().add(first, second).width(2));
                DialogActivity.sDraw = 0;
            }
        }
        if (DialogActivity.sDraw == mFlagTWO) {
            setDrawPath();
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
        DialogActivity.sDraw = 0;
    }

    @Override
    public void onClick(View v) {

    }
}
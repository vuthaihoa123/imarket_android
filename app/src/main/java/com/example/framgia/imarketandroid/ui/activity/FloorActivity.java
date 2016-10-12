package com.example.framgia.imarketandroid.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.listener.OnRecyclerItemInteractListener;
import com.example.framgia.imarketandroid.data.model.CommerceCanter;
import com.example.framgia.imarketandroid.data.model.CustomMarker;
import com.example.framgia.imarketandroid.data.model.Edge;
import com.example.framgia.imarketandroid.data.model.Floor;
import com.example.framgia.imarketandroid.data.model.Graph;
import com.example.framgia.imarketandroid.data.model.ListFloor;
import com.example.framgia.imarketandroid.data.model.Point;
import com.example.framgia.imarketandroid.data.model.Shop;
import com.example.framgia.imarketandroid.data.model.Store;
import com.example.framgia.imarketandroid.data.model.StoreType;
import com.example.framgia.imarketandroid.data.remote.RealmRemote;
import com.example.framgia.imarketandroid.ui.adapter.BookProductAdapter;
import com.example.framgia.imarketandroid.ui.adapter.ChooseStoreTypeAdapter;
import com.example.framgia.imarketandroid.ui.views.CustomMarkerView;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.HttpRequest;
import com.example.framgia.imarketandroid.util.MapUntils;
import com.example.framgia.imarketandroid.util.OnSwipeTouchListener;
import com.example.framgia.imarketandroid.util.algorithm.DijkstraAlgorithm;
import com.example.framgia.imarketandroid.util.findpath.LoadDataUtils;
import com.google.android.gms.internal.zzng;
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
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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
    .OnClickListener, BookProductAdapter.OnClickItemBarListenner, OnRecyclerItemInteractListener {
    private SlidingUpPanelLayout mSlidingLayout;
    public static int sResumeValue = 0;
    private GoogleMap mMap;
    private RecyclerView mRvDiagramOption;
    private RecyclerView.Adapter mAdapterDiagramOption;
    private RecyclerView.LayoutManager mLayoutManagerDiagramOption;
    private ListView mListFloor;
    private int sAvatar;
    private ChooseStoreTypeAdapter mAdapterStore;
    private List<StoreType> mListStore = new ArrayList<>();
    private RecyclerView mRecyclerViewStore;
    private Button mBtnDelete, mBtnGetPath, mBtnDrawPath, mBtnCurrentLocation;
    private Button mBtnDoneLocation, mBtnCancelLocation;
    private Dialog mDialog;
    private LinearLayout mLayoutFloor;
    private Switch mSwitchLocation;
    private List<Marker> mListMarker = new ArrayList<>();
    private List<Polyline> mListPolyline = new ArrayList<>();
    public static List<String> mFloorList = new ArrayList<>();
    private List<Edge> mEdges;
    private List<Point> mNodes = null;
    private LinkedList<Point> mPath;
    private RealmList<Point> mNodesDisplay = null;
    private RealmList<Point> mVertexesHoa = new RealmList<>();
    private RealmList<Edge> mEdgesHoa = new RealmList<>();
    private ChooseStoreTypeAdapter mAdapter;
    private EditText edtLocation;
    private Polyline mLine;
    private TextView mTextSwitch;
    private int mIndex, mIndexStore;
    private boolean mCheckZoom = false;
    private boolean mCheckSwitch = false;
    private boolean mCheckSlide = false;
    private boolean mCheckSlideStore = false;
    private boolean mCheckSlideFloor = false;
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
    private CustomMarker mLocationCustomMarker;
    private ImageButton mArrowImageButton;
    private TextView mTxtNameStore;
    private String mTextStoreName = "";
    private Animation mSlideRightIn, mSlideRightOut, mSlideLeftIn, mSlideLeftOut;
    private EditText edtDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_floor);
        initMap();
        hideStatusBar();
        initViews();
        Intent intent = getIntent();
        CommerceCanter commerce = (CommerceCanter)intent
            .getSerializableExtra(Constants.COMMERCE_INTENT);
        LoadDataUtils.loadFloor(this, commerce.getId());
    }


    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mNodes = RealmRemote.getAllPoint();
        mNodesDisplay = RealmRemote.getListPointDisplay(0);
        mEdges = RealmRemote.getListEdge();
        mBtnDrawPath = (Button) findViewById(R.id.btn_show_dialog);
        mBtnDrawPath.setOnClickListener(this);
        mBtnDelete = (Button) findViewById(R.id.btn_delete_data);
        mBtnDelete.setOnClickListener(this);
        mBtnGetPath = (Button) findViewById(R.id.btn_demo_find_way);
        mBtnGetPath.setOnClickListener(this);
    }

    private void initViews() {
        edtDelete = (EditText) findViewById(R.id.edt_delete);
        initListViewFloor();
        initRvStore();
        mSlideRightIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        mSlideRightOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        mSlideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        mSlideLeftOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        slideLayoutRight(mLayoutFloor, mSlideRightIn, mSlideRightOut);
        slideLayoutLeft(mRecyclerViewStore, mSlideLeftOut, mSlideLeftIn);
        mRvDiagramOption = (RecyclerView) findViewById(R.id.rv_diagram_option);
        mRvDiagramOption.setHasFixedSize(true);
        mLayoutManagerDiagramOption = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
            false);
        mAdapterDiagramOption = new BookProductAdapter(this, FakeContainer.initDiagramOption());
        mRvDiagramOption.setAdapter(mAdapterDiagramOption);
        mRvDiagramOption.setLayoutManager(mLayoutManagerDiagramOption);
        mTxtNameStore = (TextView) findViewById(R.id.txt_store_name);
        mSlidingLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mSlidingLayout.setPanelSlideListener(onSlideListener());
        mArrowImageButton = (ImageButton) findViewById(R.id.image_button_slide);
        mArrowImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckSlide == false)
                    mSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                else
                    mSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        mTextSwitch = (TextView) findViewById(R.id.mode);
        mSwitchLocation = (Switch) findViewById(R.id.switch1);
        mSwitchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTextSwitch.setText(R.string.find_store);
                    mCheckSwitch = true;
                } else {
                    mTextSwitch.setText(R.string.check_store);
                    mCheckSwitch = false;
                }
            }
        });
    }

    private void initListViewFloor() {
        mLayoutFloor = (LinearLayout) findViewById(R.id.layout_list_floor);
        mListFloor = (ListView) findViewById(R.id.list_floor);
        ArrayAdapter adapter =
            new ArrayAdapter<String>(this, R.layout.item_floor, R.id.floor_content, mFloorList);
        mListFloor.setAdapter(adapter);
        mListFloor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)
                    Toast.makeText(FloorActivity.this, R.string.floor_instruction + i, Toast
                        .LENGTH_LONG).show();
            }
        });
    }

    private void initRvStore() {
        mRecyclerViewStore = (RecyclerView) findViewById(R.id.recycler_store_type);
        mRecyclerViewStore.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mListStore = FakeContainer.initStore();
        mAdapter = new ChooseStoreTypeAdapter(this, mListStore);
        mAdapter.setOnRecyclerItemInteractListener(this);
        mRecyclerViewStore.setAdapter(mAdapter);
    }

    private void setDiaLogLocation() {
        mDialog = new Dialog(FloorActivity.this);
        mDialog.setContentView(R.layout.custom_dialog);
        mDialog.setTitle(R.string.current_location_title);
        edtLocation = (EditText) mDialog.findViewById(R.id.edtLocation);
        mBtnDoneLocation = (Button) mDialog.findViewById(R.id.done_location);
        mBtnDoneLocation.setOnClickListener(this);
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
        setListMarker();
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
                    } else
                        Toast.makeText(FloorActivity.this, R.string.input_current_location, Toast
                            .LENGTH_LONG).show();
                } else {
                    LatLngBounds.Builder builder = LatLngBounds.builder();
                    CustomMarker customMarker = mMarkerPointHashMap.get(marker);
                    if (customMarker != null && customMarker.getPosition() != null) {
                        builder.include(customMarker.getPosition());
                        final LatLngBounds bounds = builder.build();
                        try {
                            mMap.animateCamera(CameraUpdateFactory
                                .newLatLngBounds(bounds, 0));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    marker.showInfoWindow();
                }
                Point tempPoint = RealmRemote.getObjectPointFromName(marker.getTitle());
                if (tempPoint != null)
                    if (tempPoint.getType() != 0) {
                        mTxtNameStore.setText(mTextStoreName);
                    }
                return true;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (mCheckSlide) {
                    mSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    mCheckSlide = false;
                } else {
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
                        new Point(mIndexStore, 0, lat, lng, getString(R.string.C) + mIndexStore);
                    RealmRemote.savePoint(mPoint);
                    Marker locationMarket = mMap.addMarker(
                        new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude))
                            .title(mPoint.getName()));
                    locationMarket.showInfoWindow();
                }
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                FloorActivity.sResumeValue = mFlagThree;
                mTempLatLng = latLng;
                Intent intent = new Intent(FloorActivity.this, ChooseStoreTypeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setListMarker() {
        mNodesDisplay = RealmRemote.getListPointDisplay(0);
        for (Point point : mNodesDisplay) {
            MarkerOptions options = new MarkerOptions()
                .position(RealmRemote.getLocationFromName(point.getName()))
                .title(point.getName());
            Marker marker = mMap.addMarker(options);
            mListMarker.add(marker);
        }
    }

    public void setCustomMarkers(int type) {
        if (type != 0)
            setMarkerVisible(false);
        mNodesDisplay = RealmRemote.getListPointDisplay(type);
        for (Point point : mNodesDisplay) {
            drawMarker(RealmRemote.createCustomMarkerFromPoint(point));
        }
        if (mCheckCurrentLocation)
            drawMarker(mLocationCustomMarker);
        mListcustomMarker = mMarkerPointHashMap.keySet();
    }

    private void setGroundOverlay() {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable
            .picture_aeon);
        LatLngBounds newarkBounds = new LatLngBounds(
            new LatLng(21.025933, 105.896914),
            new LatLng(21.028660, 105.901372));
        GroundOverlayOptions goo =
            new GroundOverlayOptions().image(bitmapDescriptor).positionFromBounds(newarkBounds)
                .bearing(Constants.GROUND_BEARING);
        GroundOverlay imageOverlay = mMap.addGroundOverlay(goo);
        mMap.addMarker(
            new MarkerOptions().position(mAeon).title(getString(R.string.name_commerce)));
    }

    private void moveCamera() {
        setGroundOverlay();
        CameraPosition cameraPosition =
            new CameraPosition.Builder().target(mAeon).zoom((Constants.MAP_ZOOM))
                .bearing((float) FakeContainer.CAMERA_PARAMETER)
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
        for (Edge edge : edges) {
            mMap.addPolyline(new PolylineOptions()
                .add(RealmRemote.getLocationFromName(edge.getNameStart()),
                    RealmRemote.getLocationFromName(edge.getNameEnd())).width(2));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FloorActivity.sResumeValue == mFlagOne) {
            if (DialogActivity.sFirstPoint != null && DialogActivity.sFirstPoint.length() > 0 &&
                DialogActivity.sSecondPoint.length() > 0) {
                LatLng first = RealmRemote.getLocationFromName(DialogActivity.sFirstPoint);
                LatLng second;
                float results[] = new float[1];
                String[] tempListPoint =
                    DialogActivity.sSecondPoint.split(getString(R.string.comma));
                for (String tempPoint : tempListPoint) {
                    second = RealmRemote.getLocationFromName(tempPoint);
                    Location
                        .distanceBetween(first.latitude, first.longitude, second.latitude,
                            second.longitude,
                            results);
                    RealmRemote.saveEdge(
                        new Edge(DialogActivity.sFirstPoint, tempPoint, results[0]));
                    RealmRemote.saveEdge(
                        new Edge(tempPoint, DialogActivity.sFirstPoint, results[0]));
                    Polyline polyline =
                        mMap.addPolyline(new PolylineOptions().add(first, second).width(2));
                    FloorActivity.sResumeValue = 0;
                }
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
            Store store = new Store(mIndex, getString(R.string.M) + mIndex, "", 1, 1,
                ChooseStoreTypeActivity
                    .sAvatar, mTempLatLng
                .latitude, mTempLatLng
                .longitude);
            Point mPoint = store.getPoint();
            RealmRemote.saveObject(store);
            RealmRemote.savePoint(mPoint);
            drawMarker(RealmRemote.createCustomMarkerFromPoint(mPoint));
            FloorActivity.sResumeValue = 0;
        }
    }

    private void drawMarker(CustomMarker marker) {
        View markerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
            .inflate(R.layout.item_marker, null);
        CustomMarkerView customMarkerView =
            (CustomMarkerView) markerView.findViewById(R.id.custom_marker_view);
        customMarkerView.setPercentValue(marker.getNumber());
        customMarkerView.setTextforMarker();
        switch (marker.getCategory().getId()) {
            case FakeContainer.STORE_TYPE_0:
                if (mCheckCurrentLocation == true)
                    customMarkerView.setBackground(
                        ResourcesCompat.getDrawable(getResources(), Constants.LIST_AVATAR_STORE[7],
                            null));
                else
                    customMarkerView.setInvisibleBackground();
                break;
            case FakeContainer.STORE_TYPE_1:
                setBackground(customMarkerView, 0);
                break;
            case FakeContainer.STORE_TYPE_2:
                setBackground(customMarkerView, 1);
                break;
            case FakeContainer.STORE_TYPE_3:
                setBackground(customMarkerView, 2);
                break;
            case FakeContainer.STORE_TYPE_4:
                setBackground(customMarkerView, 3);
                break;
            case FakeContainer.STORE_TYPE_5:
                setBackground(customMarkerView, 4);
                break;
            case FakeContainer.STORE_TYPE_6:
                setBackground(customMarkerView, 5);
                break;
            case FakeContainer.STORE_TYPE_7:
                setBackground(customMarkerView, 6);
                break;
        }
        customMarkerView.setOnClickListener(this);
        LatLng newLatLng = new LatLng(marker.getLatitude(), marker.getLongitude());
        final Marker currentMarker = mMap.addMarker(new MarkerOptions()
            .position(newLatLng)
            .title(marker.getCategory().getName())
            .icon(BitmapDescriptorFactory
                .fromBitmap(MapUntils.createBitmapFromView(this, markerView)))
            .anchor(0.5f, 0.5f));
        mMarkerPointHashMap.put(currentMarker, marker);
    }

    private void setBackground(CustomMarkerView customMarkerView, int position) {
        if (mCheckCurrentLocation == false)
            customMarkerView.setBackground(
                ResourcesCompat.getDrawable(getResources(), Constants.LIST_AVATAR_STORE[position],
                    null));
        else
            customMarkerView.setBackground(
                ResourcesCompat
                    .getDrawable(getResources(), Constants.LIST_CURRENT_AVATAR_STORE[position],
                        null));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.custom_marker_view:
                Intent intent = new Intent(FloorActivity.this, ChooseStoreTypeActivity.class);
                startActivity(intent);
                break;
            case R.id.cancel_location:
                mDialog.dismiss();
                break;
            case R.id.btn_find_path:
                FloorActivity.sResumeValue = 2;
                startActivity(new Intent(FloorActivity.this, DialogActivity.class));
                break;
            case R.id.btn_show_dialog:
                FloorActivity.sResumeValue = 1;
                startActivity(new Intent(FloorActivity.this, DialogActivity.class));
                break;
            case R.id.btn_delete_data:
                RealmRemote.deletePoint(edtDelete.getText().toString());
            case R.id.done_location:
                if (edtLocation.getText().length() > 0) {
                    if (mCheckCurrentLocation == true) {
                        mCheckCurrentLocation = false;
                        drawMarker(mLocationCustomMarker);
                    }
                    String mLocation = edtLocation.getText().toString().toUpperCase();
                    mDialog.dismiss();
                    mCurrentLocation = RealmRemote.getObjectPointFromName(mLocation);
                    if (mCurrentLocation != null) {
                        mCheckCurrentLocation = true;
                        mLocationCustomMarker = RealmRemote.createCustomMarkerFromPoint
                            (mCurrentLocation);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(RealmRemote
                            .getLocationFromName(mLocation)));
                        drawMarker(mLocationCustomMarker);
                    } else
                        Toast.makeText(FloorActivity.this, R.string.warning_location,
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        CustomMarker customMarker = mMarkerPointHashMap.get(marker);
        startActivity(new Intent(FloorActivity.this, HomeStoreActivity.class));
    }

    @Override
    public void onClickItemBar(String textNameItem, int position) {
        switch (textNameItem) {
            case Constants.LOCATION:
                setDiaLogLocation();
                break;
            case Constants.FLOOR:
                if (mCheckSlideFloor == false) {
                    mLayoutFloor.startAnimation(mSlideRightIn);
                    mLayoutFloor.setVisibility(View.VISIBLE);
                    mCheckSlideFloor = true;
                } else {
                    mLayoutFloor.startAnimation(mSlideRightOut);
                    mLayoutFloor.setVisibility(View.INVISIBLE);
                    mCheckSlideFloor = false;
                }
                break;
            case Constants.STORE:
                if (mCheckSlideStore == false) {
                    mRecyclerViewStore.startAnimation(mSlideLeftIn);
                    mRecyclerViewStore.setVisibility(View.VISIBLE);
                    mCheckSlideStore = true;
                } else {
                    mRecyclerViewStore.startAnimation(mSlideLeftOut);
                    mRecyclerViewStore.setVisibility(View.INVISIBLE);
                    mCheckSlideStore = false;
                }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        sAvatar = position;
        if (mCheckCurrentLocation) {
            mCheckCurrentLocation = false;
            setCustomMarkers(position);
            mCheckCurrentLocation = true;
            drawMarker(mLocationCustomMarker);
        } else setCustomMarkers(position);
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
            if (customMarker != null)
                switch (customMarker.getCategory().getId()) {
                    case FakeContainer.STORE_TYPE_1:
                        mTextStoreName =
                            String.valueOf(getString(R.string.food_store)) + " " +
                                customMarker.getId();
                        break;
                    case FakeContainer.STORE_TYPE_2:
                        mTextStoreName =
                            String.valueOf(getString(R.string.fashion_name)) + " " + customMarker
                                .getId();
                        break;
                    case FakeContainer.STORE_TYPE_3:
                        mTextStoreName =
                            String.valueOf(getString(R.string.book_shop_name)) + " " + customMarker
                                .getId();
                        break;
                    case FakeContainer.STORE_TYPE_4:
                        mTextStoreName =
                            String.valueOf(getString(R.string.cosmetic_name)) + " " + customMarker
                                .getId();
                        break;
                    case FakeContainer.STORE_TYPE_5:
                        mTextStoreName =
                            String.valueOf(getString(R.string.movie_theater)) + " " + customMarker
                                .getId();
                        break;
                    case FakeContainer.STORE_TYPE_6:
                        mTextStoreName =
                            String.valueOf(getString(R.string.game_center_name)) + " " +
                                customMarker.getId();
                        break;
                }
            else {
                ImageView image = (ImageView) v.findViewById(R.id.image_marker);
                image.setVisibility(View.INVISIBLE);
                mTextStoreName = marker.getTitle();
            }
            textView.setText(mTextStoreName);
            return v;
        }
    }

    private SlidingUpPanelLayout.PanelSlideListener onSlideListener() {
        final LinearLayout temp = (LinearLayout) findViewById(R.id.layout_additional);
        return new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {
            }

            @Override
            public void onPanelCollapsed(View view) {
                mArrowImageButton.setImageResource(R.drawable.up_arrow);
                mCheckSlide = false;
            }

            @Override
            public void onPanelExpanded(View view) {
                mArrowImageButton.setImageResource(R.drawable.down_arrow);
                mCheckSlide = true;
            }

            @Override
            public void onPanelAnchored(View view) {
            }

            @Override
            public void onPanelHidden(View view) {
            }
        };
    }

    public void setDrawPath() {
        if (mListPolyline.size() > 0) {
            for (Polyline polyline : mListPolyline)
                polyline.remove();
        }
        mVertexesHoa.clear();
        for (Point p : mNodes) {
            mVertexesHoa.add(p);
        }
        mEdgesHoa.clear();
        for (Edge e : mEdges) {
            mEdgesHoa.add(e);
        }
        Graph graph = new Graph(mVertexesHoa, mEdgesHoa);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph, this);
        Point vertexF = mCurrentLocation;
        dijkstra.execute(vertexF);
        Point vertex1 = mTargetLocation;
        if (mPath != null && mPath.size() > 0)
            mPath.clear();
        mPath = dijkstra.getPath(vertex1);
        if (mPath != null) {
            for (int i = 0; i < mPath.size() - 1; i++) {
                LatLng src = RealmRemote.getLocationFromName(mPath.get(i).getName());
                LatLng dest = RealmRemote.getLocationFromName(mPath.get(i + 1).getName());
                mLine = mMap.addPolyline(
                    new PolylineOptions().add(
                        src, dest).width(2).color(Color.BLUE));
                mListPolyline.add(mLine);
            }
        } else {
            Toast
                .makeText(FloorActivity.this, R.string.current_location_warning, Toast.LENGTH_LONG)
                .show();
        }
        FloorActivity.sResumeValue = 0;
    }

    private void slideLayoutRight(final View view, final Animation a, final Animation b) {
        new OnSwipeTouchListener(view).setOnSwipeListener(
            new OnSwipeTouchListener.onSwipeEvent() {
                @Override
                public void SwipeEventDetected(View v,
                                               OnSwipeTouchListener.SwipeTypeEnum SwipeType) {
                    if (SwipeType == OnSwipeTouchListener.SwipeTypeEnum
                        .LEFT_TO_RIGHT && mCheckSlideFloor == true) {
                        view.startAnimation(b);
                        view.setVisibility(View.INVISIBLE);
                        mCheckSlideFloor = false;
                    }
                }
            });
    }

    private void slideLayoutLeft(final View view, final Animation a, final Animation b) {
        new OnSwipeTouchListener(view).setOnSwipeListener(
            new OnSwipeTouchListener.onSwipeEvent() {
                @Override
                public void SwipeEventDetected(View v,
                                               OnSwipeTouchListener.SwipeTypeEnum SwipeType) {
                    if (SwipeType == OnSwipeTouchListener.SwipeTypeEnum
                        .RIGHT_TO_LEFT && mCheckSlideStore == true) {
                        view.startAnimation(a);
                        view.setVisibility(View.INVISIBLE);
                        mCheckSlideStore = false;
                    }
                }
            });
    }
}

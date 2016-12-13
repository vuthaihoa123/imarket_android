package com.example.framgia.imarketandroid.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.listener.OnFinishLoadDataListener;
import com.example.framgia.imarketandroid.data.listener.OnRecyclerItemInteractListener;
import com.example.framgia.imarketandroid.data.model.CommerceCanter;
import com.example.framgia.imarketandroid.data.model.CustomMarker;
import com.example.framgia.imarketandroid.data.model.Edge;
import com.example.framgia.imarketandroid.data.model.Graph;
import com.example.framgia.imarketandroid.data.model.Point;
import com.example.framgia.imarketandroid.data.model.StoreType;
import com.example.framgia.imarketandroid.data.remote.RealmRemote;
import com.example.framgia.imarketandroid.ui.adapter.BookProductAdapter;
import com.example.framgia.imarketandroid.ui.adapter.ChooseStoreTypeAdapter;
import com.example.framgia.imarketandroid.ui.views.CustomMarkerView;
import com.example.framgia.imarketandroid.ui.widget.LinearItemDecoration;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.MapUntils;
import com.example.framgia.imarketandroid.util.algorithm.DijkstraAlgorithm;
import com.example.framgia.imarketandroid.util.findpath.LoadDataUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
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
        .OnClickListener, BookProductAdapter.OnClickItemBarListenner, OnRecyclerItemInteractListener,
        SensorEventListener, OnFinishLoadDataListener {
    public final static int FLAG_CHECK_LIST_SAVE = 7;
    public static int sResumeValue = 0;
    public static List<String> mFloorList = new ArrayList<>();
    public static boolean sCheckSlideStore = false;
    public static boolean sCheckSlideFloor = false;
    public static Point sCurrentLocation;
    public static Point sSavedLocation;
    public static String sSavedNote;
    public static float currentDegree = FakeContainer.CAMERA_PARAMETER;
    private final float DIS_LEVEL_FOUR = 40;
    private final float DIS_LEVEL_THREE = 80;
    private final float DIS_LEVEL_TWO = 120;
    private final float DIS_LEVEL_ONE = 160;
    private final int START_LEVEL_FOUR = 10;
    private final int START_LEVEL_THREE = 20;
    private final int START_LEVEL_TWO = 30;
    private final int START_LEVEL_ONE = 40;
    private final float ZOOM_LEVEL_THREE = 17.5f;
    private final float ZOOM_LEVEL_TWO = 18.5f;
    private final float ZOOM_LEVEL_ONE = 19.5f;
    private final float LOWER_BOUNDS_ZOOM = 15;
    private GoogleMap mMap;
    private RecyclerView mRvDiagramOption;
    private RecyclerView.Adapter mAdapterDiagramOption;
    private ListView mListFloor;
    private List<StoreType> mListStore = new ArrayList<>();
    private RecyclerView mRecyclerViewStore;
    private Button mBtnDoneLocation;
    private Dialog mDialog;
    private LinearLayout mLayoutFloor;
    private SwitchCompat mSwitchLocation;
    private List<Marker> mListMarker = new ArrayList<>();
    private List<Polyline> mListPolyline = new ArrayList<>();
    private List<Edge> mEdges;
    private List<Point> mNodes = null;
    private LinkedList<Point> mPath;
    private RealmList<Point> mNodesDisplay = null;
    private RealmList<Point> mVertexesHoa = new RealmList<>();
    private RealmList<Edge> mEdgesHoa = new RealmList<>();
    private ChooseStoreTypeAdapter mAdapter;
    private EditText mEdtLocation;
    private Polyline mLine;
    private TextView mTextSwitch;
    private int mIndex, mIndexSaveLocation;
    private boolean mCheckZoom = false;
    private boolean mCheckSwitch = false;
    private boolean mCheckSlide = false;
    private boolean mCheckCurrentLocation = false;
    private boolean mCheckSaveLocation = false;
    private Point mTargetLocation;
    private int mFlagOne = 1;
    private int mFlagTWO = 2;
    private int mFlagThree = 3;
    private int mFlagSavePosition = 4;
    private int mFlagSpinner = 5;
    private LatLng mTempLatLng, mCurrentlatLng;
    private HashMap<Marker, CustomMarker> mMarkerPointHashMap = new HashMap<>();
    private Collection<Marker> mListcustomMarker = new ArrayList<>();
    private LatLng mAeon = new LatLng(21.026975, 105.899302);
    private CustomMarker mLocationCustomMarker;
    private CustomMarker mSaveCustomarker;
    private String mTextStoreName = "";
    private Animation mSlideRightIn, mSlideRightOut, mSlideLeftIn, mSlideLeftOut;
    private LinearLayoutCompat mImgSavePoint;
    private SensorManager mSensorManager;
    private View mMarkerView;
    private View mInterMarkerView;
    private View mSaveView;
    private float mAngleMap = 0;
    private float mZoom = 18;
    private float mBearing = FakeContainer.CAMERA_PARAMETER;
    private CustomMarkerView mIntersectionMK;
    private CustomMarkerView mSaveMK;
    private Marker mInteraker;
    private Marker mSaveMarker;
    private LinearLayoutCompat mNameLoad;
    private FloatingActionButton mFABhire, mFABnewStore, mFABsaleOff;
    private LoadDataUtils mDataUtils;
    private TextView mTextViewNameCenter;
    private CommerceCanter mCommerce;
    private int mScaledMarkerSize = 30;
    private float mMinZoomLevel = 0;
    private float mMaxZoomLevel = 0;
    private CustomMarkerView mGeneralCustomMarkerView;
    private ArrayList<Marker> mMarkerList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_floor);
        initMap();
        hideStatusBar();
        initViews();
        Intent intent = getIntent();
        mCommerce = (CommerceCanter) intent
                .getSerializableExtra(Constants.COMMERCE_INTENT);
        mDataUtils = new LoadDataUtils();
        mDataUtils.init(this);
        mDataUtils.loadFloor(this, mCommerce.getId());
        mDataUtils.setLoadDataListener(FloorActivity.this);
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mNodes = RealmRemote.getAllPoint();
        mNodesDisplay = RealmRemote.getListPointDisplay(0);
        mEdges = RealmRemote.getListEdge();
        mNameLoad = (LinearLayoutCompat) findViewById(R.id.btn_name_refresh);
        mFABhire = (FloatingActionButton) findViewById(R.id.hire);
        mFABnewStore = (FloatingActionButton) findViewById(R.id.new_store);
        mFABsaleOff = (FloatingActionButton) findViewById(R.id.sale_off);
        mFABhire.setOnClickListener(this);
        mFABnewStore.setOnClickListener(this);
        mFABsaleOff.setOnClickListener(this);
        mNameLoad.setOnClickListener(this);
    }

    private void initViews() {
        mMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.item_marker, null);
        mInterMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.item_current_marker, null);
        mSaveView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.item_current_marker, null);
        mIntersectionMK =
                (CustomMarkerView) mInterMarkerView.findViewById(R.id.current_custom_marker_view);
        mSaveMK =
                (CustomMarkerView) mSaveView.findViewById(R.id.current_custom_marker_view);
        mImgSavePoint = (LinearLayoutCompat) findViewById(R.id.img_save_point);
        mImgSavePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sCurrentLocation != null) {
                    FloorActivity.sResumeValue = mFlagSavePosition;
                    startActivity(new Intent(FloorActivity.this, SavePointActivity.class));
                } else
                    Toast.makeText(FloorActivity.this, R.string.input_current_location, Toast
                            .LENGTH_LONG).show();
            }
        });
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        mEdtDelete = (EditText) findViewById(R.id.edt_delete);
        initListViewFloor();
        initRvStore();
        mSlideRightIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        mSlideRightOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        mSlideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        mSlideLeftOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        MapUntils.slideLayoutRight(mLayoutFloor, mSlideRightIn, mSlideRightOut);
        MapUntils.slideLayoutLeft(mRecyclerViewStore, mSlideLeftOut, mSlideLeftIn);
        mRvDiagramOption = (RecyclerView) findViewById(R.id.rv_diagram_option);
        mRvDiagramOption
                .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvDiagramOption.setHasFixedSize(true);
        mAdapterDiagramOption = new BookProductAdapter(this, FakeContainer.initDiagramOption());
        mRvDiagramOption.setAdapter(mAdapterDiagramOption);
        mTextSwitch = (TextView) findViewById(R.id.iv_mode);
        mTextSwitch.setSelected(true);
        mSwitchLocation = (SwitchCompat) findViewById(R.id.switch_mode);
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
        mTextViewNameCenter = (TextView) findViewById(R.id.txt_name_center);
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
            }
        });
    }

    private void initRvStore() {
        mRecyclerViewStore = (RecyclerView) findViewById(R.id.recycler_store_type);
        mRecyclerViewStore.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerViewStore.addItemDecoration(new LinearItemDecoration(this,
                getResources().getDimensionPixelSize(R.dimen.common_size_1)));
        mListStore = FakeContainer.initStore();
        mAdapter = new ChooseStoreTypeAdapter(this, mListStore);
        mAdapter.setOnRecyclerItemInteractListener(this);
        mRecyclerViewStore.setAdapter(mAdapter);
    }

    private void setDiaLogLocation() {
        mDialog = new Dialog(FloorActivity.this);
        mDialog.setContentView(R.layout.custom_dialog);
        mDialog.setTitle(R.string.current_location_title);
        mEdtLocation = (EditText) mDialog.findViewById(R.id.edtLocation);
        mBtnDoneLocation = (Button) mDialog.findViewById(R.id.done_location);
        mBtnDoneLocation.setOnClickListener(this);
        mEdtLocation.addTextChangedListener(new TextWatcher() {
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
        getZoomLevel(mMap);
        moveCamera();
//        setListMarker();
//        setListEdge();
        setCustomMarkers(0);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setInfoWindowAdapter(new MarkerInfoAdapter());
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (mCheckSwitch) {
                    if (mCheckCurrentLocation == true) {
                        mTargetLocation = RealmRemote.getObjectPointFromName(Integer.parseInt(marker
                                .getTitle()));
                        setDrawPath();
                    } else
                        Toast.makeText(FloorActivity.this, R.string.input_current_location, Toast
                                .LENGTH_LONG).show();
                } else {
                    LatLng temp = marker.getPosition();
                    CameraPosition cameraPosition =
                            new CameraPosition.Builder().target(temp).zoom(mZoom).bearing(mBearing)
                                    .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    CustomMarker customMarker = mMarkerPointHashMap.get(marker);
                    if (customMarker != null && customMarker.getPosition() != null) {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    marker.showInfoWindow();
                }
                Point tempPoint = null;
                if (marker.getTitle().length() > 4)
                    tempPoint = RealmRemote.getObjectPointFromName(Integer.parseInt(marker
                            .getTitle()));
                if (tempPoint != null)
                    if (tempPoint.getType() != 0) {
                    }
                return true;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (sCheckSlideStore) {
                    StoreAppear(false);
                    sCheckSlideStore = false;
                }
                if (sCheckSlideFloor) {
                    FloorAppear(false);
                    sCheckSlideFloor = true;
                }
                if (mCheckSlide) {
                    mCheckSlide = false;
                }
            }
        });
        Bundle bundle = new Bundle();
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                SharedPreferences preferences =
                        getSharedPreferences(getString(R.string.share_point), MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                mIndexSaveLocation = preferences.getInt(getString(R.string.idSavePoint), -1);
                mIndexSaveLocation--;
                editor.putInt(getString(R.string.idSavePoint), mIndexSaveLocation);
                editor.commit();
                Point point =
                        new Point(mIndexSaveLocation, Constants.SAVE_POINT_TYPE, latLng.latitude, latLng
                                .longitude, 1);
                RealmRemote.savePoint(point);
                FloorActivity.sResumeValue = mFlagSpinner;
                Intent intent = new Intent(FloorActivity.this, SavePointActivity.class);
                intent.putExtra(Constants.BUNDLE_SAVE_POINT, point);
                startActivity(intent);
            }
        });
    }

    private void setListMarker() {
        mNodesDisplay = RealmRemote.getListPointDisplay(0);
        for (Point point : mNodesDisplay) {
            //  if (point.getType() != 0) {
            MarkerOptions options = new MarkerOptions()
                    .position(RealmRemote.getLocationFromName(point.getId()))
                    .title(String.valueOf(point.getId()));
            Marker marker = mMap.addMarker(options);
            mListMarker.add(marker);
            //   }
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
            private float mCurrentZoom = -1;

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                float changeZoomLevel = cameraPosition.zoom - mCurrentZoom;
                if (changeZoomLevel == 0)
                    return;
                mCurrentZoom = cameraPosition.zoom;

                float angle = cameraPosition.bearing;
                mBearing = cameraPosition.bearing;
                mAngleMap = angle - FakeContainer.CAMERA_PARAMETER;
                mZoom = cameraPosition.zoom;

                float diffZoomLevel = mMaxZoomLevel - LOWER_BOUNDS_ZOOM;

                /*Determine value of scale marker that is suitable when zooming (in/out) maps:
                * MaxZoomLevel = 21 and LowerBoundsZoom = 15
                * The rate of change of marker when zooming maps that is differrent belong to the value of cameraPosition.zoom*/
                if (mCurrentZoom > ZOOM_LEVEL_ONE) {
                    mScaledMarkerSize = START_LEVEL_ONE + (int) ((mCurrentZoom - LOWER_BOUNDS_ZOOM) * DIS_LEVEL_ONE / diffZoomLevel);
                } else if (mCurrentZoom > ZOOM_LEVEL_TWO) {
                    mScaledMarkerSize = START_LEVEL_TWO + (int) ((mCurrentZoom - LOWER_BOUNDS_ZOOM) * DIS_LEVEL_TWO / diffZoomLevel);
                } else if (mCurrentZoom > ZOOM_LEVEL_THREE) {
                    mScaledMarkerSize = START_LEVEL_THREE + (int) ((mCurrentZoom - LOWER_BOUNDS_ZOOM) * DIS_LEVEL_THREE / diffZoomLevel);
                } else {
                    mScaledMarkerSize = START_LEVEL_FOUR + (int) ((mCurrentZoom - LOWER_BOUNDS_ZOOM) * DIS_LEVEL_FOUR / diffZoomLevel);
                }

                /*Resize each marker when zooming maps:*/
                for (int i = 0; i < mNodesDisplay.size(); i++) {
                    Point point = mNodesDisplay.get(i);
                    updateCustomMarkerFromPoint(RealmRemote.createCustomMarkerFromPoint(point), i);
                }
            }
        });
    }

    private void updateCustomMarkerFromPoint(CustomMarker cMarker, int index) {
        switch (cMarker.getType()) {
            case FakeContainer.STORE_TYPE_1:
                setBackground(mGeneralCustomMarkerView, 1);
                break;
            case FakeContainer.STORE_TYPE_2:
                setBackground(mGeneralCustomMarkerView, 2);
                break;
            case FakeContainer.STORE_TYPE_3:
                setBackground(mGeneralCustomMarkerView, 3);
                break;
            case FakeContainer.STORE_TYPE_4:
                setBackground(mGeneralCustomMarkerView, 4);
                break;
            case FakeContainer.STORE_TYPE_5:
                setBackground(mGeneralCustomMarkerView, 5);
                break;
            case FakeContainer.STORE_TYPE_6:
                setBackground(mGeneralCustomMarkerView, 6);
                break;
            case FakeContainer.STORE_TYPE_7:
                setBackground(mGeneralCustomMarkerView, 7);
                break;
            default:
                break;
        }
        Bitmap bmp = MapUntils.createBitmapFromView(this, mMarkerView);
        if (mMarkerList.size() > 0) {
            mMarkerList.get(index).setIcon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(bmp, mScaledMarkerSize)));
        }
    }

    private Bitmap resizeBitmap(Bitmap bmp, int scale) {
        return Bitmap.createScaledBitmap(bmp, scale, scale, false);
    }

    private void getZoomLevel(GoogleMap googleMap) {
        mMinZoomLevel = googleMap.getMinZoomLevel();
        mMaxZoomLevel = googleMap.getMaxZoomLevel();
    }

    private void setMarkerVisible(boolean check) {
        for (Marker marker : mListcustomMarker) {
            marker.setVisible(check);
        }
    }

    private void setListEdge() {
        RealmResults<Edge> edges = RealmRemote.getListEdgeDisplay();
        for (Edge edge : edges) {
            mMap.addPolyline(new PolylineOptions()
                    .add(RealmRemote.getLocationFromName(edge.getNameStart()),
                            RealmRemote.getLocationFromName(edge.getNameEnd())).width(4));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor
                .TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        if (FloorActivity.sResumeValue == mFlagOne) {
            if (DialogActivity.sFirstPoint > 0 &&
                    DialogActivity.sSecondPoint.length() > 0) {
                LatLng first =
                        RealmRemote.getLocationFromName(DialogActivity.sFirstPoint);
                LatLng second;
                float results[] = new float[1];
                String[] tempListPoint =
                        DialogActivity.sSecondPoint.split(getString(R.string.comma));
                for (String tempPoint : tempListPoint) {
                    second = RealmRemote.getLocationFromName(Integer.parseInt(tempPoint.trim()));
                    Location
                            .distanceBetween(first.latitude, first.longitude, second.latitude,
                                    second.longitude,
                                    results);
                    RealmRemote.saveEdge(
                            new Edge(DialogActivity.sFirstPoint, Integer.parseInt(tempPoint),
                                    results[0]));
                    RealmRemote.saveEdge(
                            new Edge(Integer.parseInt(tempPoint), DialogActivity.sFirstPoint,
                                    results[0]));
                    Polyline polyline =
                            mMap.addPolyline(new PolylineOptions().add(first, second).width(4));
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
            Point mPoint = new Point(mIndex, ChooseStoreTypeActivity
                    .sAvatar, mTempLatLng.latitude, mTempLatLng.longitude, 1);
            RealmRemote.savePoint(mPoint);
            drawMarker(RealmRemote.createCustomMarkerFromPoint(mPoint));
            FloorActivity.sResumeValue = 0;
        }
        if (FloorActivity.sResumeValue == mFlagSavePosition) {
            if (SavePointActivity.sCheckpath != -1 && SavePointActivity
                    .sCheckpath != sCurrentLocation.getId()) {
                mTargetLocation = RealmRemote.getObjectPointFromId(SavePointActivity.sCheckpath);
                setDrawPath();
            }
            FloorActivity.sResumeValue = 0;
        }
        if (FloorActivity.sResumeValue == FLAG_CHECK_LIST_SAVE) {
            if (sSavedLocation != null && SavePointActivity.sCheckpath == 2) {
                if (mCheckSaveLocation == true) {
                    mCheckSaveLocation = false;
                    drawMarker(mSaveCustomarker);
                }
                mCheckSaveLocation = true;
                mSaveCustomarker = RealmRemote.createCustomMarkerFromPoint(sSavedLocation);
                drawMarker(mSaveCustomarker);
            }
            FloorActivity.sResumeValue = 0;
        }
    }

    private void drawMarker(CustomMarker marker) {
        mGeneralCustomMarkerView = (CustomMarkerView) mMarkerView.findViewById(R.id.custom_marker_view);
        mGeneralCustomMarkerView.setPercentValue(0);
        mGeneralCustomMarkerView.setTextforMarker();
        mIntersectionMK.setPercentValue(0);
        mIntersectionMK.setTextforMarker();
        mSaveMK.setPercentValue(0);
        mSaveMK.setTextforMarker();
        switch (marker.getType()) {
            case Constants.SAVE_POINT_TYPE:
                if (mCheckSaveLocation == true) {
                    mSaveMK
                            .setBackground(getResources().getDrawable(R.drawable.ic_save_point));
                    LatLng newLatLng = new LatLng(marker.getLatitude(), marker.getLongitude());
                    mSaveMarker = mMap.addMarker(new MarkerOptions()
                            .position(newLatLng)
                            .title(marker.getName())
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(MapUntils.createBitmapFromView(this, mSaveView)))
                            .anchor(0.5f, 0.5f));
                    mMarkerPointHashMap.put(mSaveMarker, marker);
                } else {
                    if (mSaveMarker != null)
                        mSaveMarker.remove();
                }
                return;
            case FakeContainer.STORE_TYPE_0:
                if (mCheckCurrentLocation == true) {
                    mIntersectionMK
                            .setBackground(getResources().getDrawable(R.drawable.compass));
                    LatLng newLatLng = new LatLng(marker.getLatitude(), marker.getLongitude());
                    mInteraker = mMap.addMarker(new MarkerOptions()
                            .position(newLatLng)
                            .title(marker.getName())
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(MapUntils.createBitmapFromView(this, mInterMarkerView)))
                            .anchor(0.5f, 0.5f));
                } else {
                    if (mInteraker != null)
                        mInteraker.remove();
                }
                return;
            case FakeContainer.STORE_TYPE_1:
                setBackground(mGeneralCustomMarkerView, 1);
                break;
            case FakeContainer.STORE_TYPE_2:
                setBackground(mGeneralCustomMarkerView, 2);
                break;
            case FakeContainer.STORE_TYPE_3:
                setBackground(mGeneralCustomMarkerView, 3);
                break;
            case FakeContainer.STORE_TYPE_4:
                setBackground(mGeneralCustomMarkerView, 4);
                break;
            case FakeContainer.STORE_TYPE_5:
                setBackground(mGeneralCustomMarkerView, 5);
                break;
            case FakeContainer.STORE_TYPE_6:
                setBackground(mGeneralCustomMarkerView, 6);
                break;
            case FakeContainer.STORE_TYPE_7:
                setBackground(mGeneralCustomMarkerView, 7);
                break;
            default:
                break;
        }
        mGeneralCustomMarkerView.setOnClickListener(this);
        LatLng newLatLng = new LatLng(marker.getLatitude(), marker.getLongitude());
        final Marker currentMarker = mMap.addMarker(new MarkerOptions()
                .position(newLatLng)
                .title(marker.getName())
                .icon(BitmapDescriptorFactory
                        .fromBitmap(resizeBitmap(MapUntils.createBitmapFromView(this, mMarkerView), mScaledMarkerSize)))
                .anchor(0.5f, 0.5f));
        mMarkerPointHashMap.put(currentMarker, marker);
        mMarkerList.add(currentMarker);
    }

    private void setBackground(CustomMarkerView customMarkerView, int position) {
        if (customMarkerView == null)
            return;
        customMarkerView.setVisible(true);
        if (mCheckCurrentLocation == false)
            customMarkerView.setBackground(
                    ResourcesCompat.getDrawable(getResources(),
                        Constants.DataList.LIST_AVATAR_STORE[position],
                            null));
        else
            customMarkerView.setBackground(
                    ResourcesCompat
                            .getDrawable(getResources(),
                                Constants.DataList.LIST_CURRENT_AVATAR_STORE[position],
                                    null));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_name_refresh:
                finish();
                startActivity(getIntent());
                break;
            case R.id.custom_marker_view:
                Intent intent = new Intent(FloorActivity.this, ChooseStoreTypeActivity.class);
                startActivity(intent);
                break;
            case R.id.cancel_location:
                mDialog.dismiss();
                break;
            case R.id.done_location:
                if (mEdtLocation.getText().length() > 0) {
                    if (mCheckCurrentLocation == true) {
                        mCheckCurrentLocation = false;
                        drawMarker(mLocationCustomMarker);
                    }
                    int mLocation = Integer.parseInt(mEdtLocation.getText().toString().trim());
                    mDialog.dismiss();
                    sCurrentLocation = RealmRemote.getObjectPointFromName(mLocation);
                    if (sCurrentLocation != null) {
                        mCheckCurrentLocation = true;
                        mLocationCustomMarker = RealmRemote.createCustomMarkerFromPoint
                                (sCurrentLocation);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(RealmRemote
                                .getLocationFromName(mLocation)));
                        drawMarker(mLocationCustomMarker);
                    } else
                        Toast.makeText(FloorActivity.this, R.string.warning_location,
                                Toast.LENGTH_LONG).show();
                    //   rotateMarker(mInteraker, 360);
                }
                if (mListPolyline.size() > 0) {
                    for (Polyline polyline : mListPolyline)
                        polyline.remove();
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
                //setDiaLogLocation();
                scanQrCode();
                break;
            case Constants.FLOOR:
                if (mFloorList.size() == 0) mFloorList.add(getString(R.string.only_one));
                if (!sCheckSlideFloor) {
                    if (sCheckSlideStore) {
                        StoreAppear(false);
                        sCheckSlideStore = false;
                    }
                    FloorAppear(true);
                    sCheckSlideFloor = true;
                } else {
                    FloorAppear(false);
                    sCheckSlideFloor = false;
                }
                break;
            case Constants.STORE:
                if (!sCheckSlideStore) {
                    if (sCheckSlideFloor) {
                        FloorAppear(false);
                        sCheckSlideFloor = false;
                    }
                    StoreAppear(true);
                    sCheckSlideStore = true;
                } else {
                    StoreAppear(false);
                    sCheckSlideStore = false;
                }
                break;
            case Constants.DIRECTION:
                startActivity(new Intent(FloorActivity.this, DirectionToMarket.class));
                break;
            case Constants.NEAR_POINT:
                FloorActivity.sResumeValue = FLAG_CHECK_LIST_SAVE;
                startActivity(new Intent(FloorActivity.this, SavePointActivity.class));
                break;
        }
    }

    private void FloorAppear(boolean appear) {
        if (appear) {
            mLayoutFloor.startAnimation(mSlideRightIn);
            mLayoutFloor.setVisibility(View.VISIBLE);
        } else {
            mLayoutFloor.startAnimation(mSlideRightOut);
            mLayoutFloor.setVisibility(View.INVISIBLE);
        }
    }

    private void StoreAppear(boolean appear) {
        if (appear) {
            mRecyclerViewStore.startAnimation(mSlideLeftIn);
            mRecyclerViewStore.setVisibility(View.VISIBLE);
        } else {
            mRecyclerViewStore.startAnimation(mSlideLeftOut);
            mRecyclerViewStore.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mCheckCurrentLocation) {
            mCheckCurrentLocation = false;
            setCustomMarkers(position);
            mCheckCurrentLocation = true;
            drawMarker(mLocationCustomMarker);
        } else setCustomMarkers(position);
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
        Point vertexF = sCurrentLocation;
        dijkstra.execute(vertexF);
        Point vertex1 = mTargetLocation;
        if (mPath != null && mPath.size() > 0)
            mPath.clear();
        mPath = dijkstra.getPath(vertex1);
        if (mPath != null) {
            for (int i = 0; i < mPath.size() - 1; i++) {
                LatLng src = RealmRemote.getLocationFromName(mPath.get(i).getId());
                LatLng dest = RealmRemote.getLocationFromName(mPath.get(i + 1).getId());
                mLine = mMap.addPolyline(
                        new PolylineOptions().add(
                                src, dest).width(4).color(Color.RED));
                mListPolyline.add(mLine);
            }
        } else {
            Toast
                    .makeText(FloorActivity.this, R.string.current_location_warning, Toast.LENGTH_LONG)
                    .show();
        }
        FloorActivity.sResumeValue = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float degree = Math.round(event.values[0]) + (mAngleMap);
        if (mInteraker != null) {
            rotateMarker(mInteraker, degree);
        }
        currentDegree = degree;
    }

    public void rotateMarker(final Marker marker, final float toRotation) {
        final Handler handler = new Handler();
        final float[] rotation = {currentDegree};
        handler.post(new Runnable() {
            @Override
            public void run() {
                rotation[0] += 0.5;
                marker.setRotation(rotation[0]);
                if (rotation[0] < toRotation) {
                    handler.postDelayed(this, 5);
                }
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 20)
            if (resultCode == RESULT_OK) {
                String content = data.getStringExtra("SCAN_RESULT");
                if (mCheckCurrentLocation == true) {
                    mCheckCurrentLocation = false;
                    drawMarker(mLocationCustomMarker);
                }
                int mLocation = Integer.parseInt(content);
                sCurrentLocation = RealmRemote.getObjectPointFromName(mLocation);
                if (sCurrentLocation != null) {
                    mCheckCurrentLocation = true;
                    mLocationCustomMarker = RealmRemote.createCustomMarkerFromPoint
                            (sCurrentLocation);
                    mCurrentlatLng = RealmRemote
                            .getLocationFromName(mLocation);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentlatLng));
                    drawMarker(mLocationCustomMarker);
                } else
                    Toast.makeText(FloorActivity.this, R.string.warning_location,
                            Toast.LENGTH_LONG).show();
            }
    }

    public void scanQrCode() {
        try {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes
            startActivityForResult(intent, 20);
        } catch (Exception e) {
            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            startActivity(marketIntent);
        }
    }

    @Override
    public void onFinish(int result) {
        if(result == Constants.ResultFinishLoadData.LOAD_DATA_FINISH)
            mTextViewNameCenter.setText(mCommerce.getName());
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
            TextView hire_text = (TextView) v.findViewById(R.id.text_hire);
            TextView sale_text = (TextView) v.findViewById(R.id.text_sale);
            CustomMarker customMarker = mMarkerPointHashMap.get(marker);
            if (customMarker != null)
                switch (customMarker.getType()) {
                    case FakeContainer.STORE_TYPE_1:
                        mTextStoreName =
                                Constants.DataList.LIST_NAME_STORE[1];
                        break;
                    case FakeContainer.STORE_TYPE_2:
                        mTextStoreName =
                                Constants.DataList.LIST_NAME_STORE[2];
                        break;
                    case FakeContainer.STORE_TYPE_3:
                        mTextStoreName =
                                Constants.DataList.LIST_NAME_STORE[3];
                        break;
                    case FakeContainer.STORE_TYPE_4:
                        mTextStoreName =
                                Constants.DataList.LIST_NAME_STORE[4];
                        break;
                    case FakeContainer.STORE_TYPE_5:
                        mTextStoreName =
                                Constants.DataList.LIST_NAME_STORE[5];
                        break;
                    case FakeContainer.STORE_TYPE_6:
                        mTextStoreName =
                                Constants.DataList.LIST_NAME_STORE[6];
                        break;
                    case FakeContainer.STORE_TYPE_7:
                        mTextStoreName =
                                Constants.DataList.LIST_NAME_STORE[7];
                        break;
                    case Constants.SAVE_POINT_TYPE:
                        LinearLayout layoutHire = (LinearLayout) v.findViewById(R.id.layout_hire);
                        LinearLayout layoutSale = (LinearLayout) v.findViewById(R.id.layout_sale);
                        ((ViewGroup) v).removeView(layoutHire);
                        ((ViewGroup) v).removeView(layoutSale);
                        mTextStoreName = FloorActivity.sSavedNote;
                        break;
                }
            else {
                LinearLayout layoutHire = (LinearLayout) v.findViewById(R.id.layout_hire);
                LinearLayout layoutSale = (LinearLayout) v.findViewById(R.id.layout_sale);
                ((ViewGroup) v).removeView(layoutHire);
                ((ViewGroup) v).removeView(layoutSale);
                mTextStoreName = Constants.DataList.LIST_NAME_STORE[0] + " " + marker.getTitle();
            }
            textView.setText(mTextStoreName);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            return v;
        }
    }
}
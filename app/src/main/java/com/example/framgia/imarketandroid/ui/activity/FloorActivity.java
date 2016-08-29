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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Edge;
import com.example.framgia.imarketandroid.data.model.Graph;
import com.example.framgia.imarketandroid.data.model.Point;
import com.example.framgia.imarketandroid.data.remote.RealmRemote;
import com.example.framgia.imarketandroid.data.model.Floor;
import com.example.framgia.imarketandroid.data.model.Shop;
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
import com.google.android.gms.maps.model.PolygonOptions;
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
    ArrayAdapter<Shop> mAdapterShop;
    ArrayAdapter<Floor> mAdapterFloor;
    private Spinner mSpinerFloor, mSpinnerProduct;
    private List<Shop> mShopList = new ArrayList<>();
    private List<Floor> mFloorList = new ArrayList<>();
    public static int statement = 0;
    Button control_but, delete_data, draw_path, getPath;
    PolygonOptions rectOptions = new PolygonOptions();
//    DatabaseRemote remote;
    private GoogleMap mMap;
    private RealmResults<Point> mNodes= null;
    private RealmList<Point> mNodesDisplay= null;
    private RealmList<Point> mVertexesHoa = new RealmList<>();
    private RealmList<Edge> mEdgesHoa = new RealmList<>();
    private List<Edge> mEdges;
    private Dialog mDialog;
    private LinearLayout mLayoutLocation;
    private Button mBtnDoneLocation, mBtnCancelLocation;
    private String mMarkLocation;
    private Polyline mline;
    private boolean mCheckZoom=false;
    private List<Marker> mListMarker= new ArrayList<>();
    private int mMileStones=0, mStore=0;
    private int mIndex, mIndexStore;
    private int mCheckCurrent;
    private Point mCurrentLocation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_floor);
//        SharedPreferences preferences= getSharedPreferences("id_point", MODE_PRIVATE);
//        SharedPreferences.Editor editor= preferences.edit();
//        mIndex=preferences.getInt("idPoint",0);
        initMap();
        hideStatusBar();
        initViews();
        createDataForFloor();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mNodes= RealmRemote.getAllPoint();
        mNodesDisplay=RealmRemote.getListPointDisplay();
        mEdges= RealmRemote.getListEdge();
        control_but = (Button) findViewById(R.id.show_dialog);
        delete_data = (Button) findViewById(R.id.delete_data);
        draw_path = (Button) findViewById(R.id.find_path);
        draw_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FloorActivity.statement = 2;
                Intent intent = new Intent(FloorActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });
        getPath=(Button)findViewById(R.id.demo_find_way);
        delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        control_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorActivity.statement = 1;
                Intent intent = new Intent(FloorActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
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
                    String mLocation = edtLocation.getText().toString();
                    mDialog.dismiss();
                    LatLng currentLatLng= null;
                    currentLatLng = RealmRemote.getLocationFromName(mLocation);
                    if(currentLatLng!= null) {
                        for (int i = 0; i < mListMarker.size(); i++) {
                            mListMarker.get(i).showInfoWindow();
                        }
                        setListMarker();
                        Marker marker = mMap.addMarker(new MarkerOptions().position(currentLatLng)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                        marker.showInfoWindow();
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
                    }
                    else
                        Toast.makeText(FloorActivity.this, "Khong tim thay cho nay", Toast.LENGTH_LONG).show();
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

                if( editable.length()>0){
                    mBtnDoneLocation.setEnabled(true);
                }
                else
                    mBtnDoneLocation.setEnabled(false);
            }
        });

        mDialog.show();
    }

    private void createDataForFloor() {
        Floor floor = new Floor(0, "Choose floor");
        Floor floor1 = new Floor(1, "Tầng 1");
        Floor floor2 = new Floor(2, "Tầng 2");
        mFloorList.add(floor);
        mFloorList.add(floor1);
        mFloorList.add(floor2);
        mAdapterFloor.notifyDataSetChanged();
    }

    private void createDataForShop() {
        if (mSpinerFloor.getSelectedItemPosition() > 0) {
            Shop shop1 = new Shop(1, "Quần Áo");
            Shop shop2 = new Shop(2, "Váy");
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
        if (mEdges.size() > 0)
            for (int i = 0; i < mEdges.size(); i++) {

            }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                double lat = latLng.latitude;
                double lng = latLng.longitude;
                SharedPreferences preferences= getSharedPreferences("id_point", MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                mIndex=preferences.getInt("idPoint", 0);
                mIndex++;
                editor.putInt("idPoint", mIndex);
                editor.commit();
                Point mPoint= new Point(mIndex , lat, lng,2 ,"M"+mIndex);
                RealmRemote.savePoint(mPoint);
                Marker locationMarket = mMap.addMarker(
                        new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude))
                                .title(":"+mPoint.getName()));
                locationMarket.showInfoWindow();
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                double lat = latLng.latitude;
                double lng = latLng.longitude;
                SharedPreferences preferences= getSharedPreferences("id_store", MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                mIndexStore=preferences.getInt("idStore", 0);
                mIndexStore++;
                editor.putInt("idStore", mIndexStore);
                editor.commit();
                Point mPoint= new Point(mIndexStore, lat, lng,1, "C"+mIndexStore);
                RealmRemote.savePoint(mPoint);
                Marker locationMarket = mMap.addMarker(
                        new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude))
                                .title(":"+mPoint.getName()));
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
                new GroundOverlayOptions().image(BD).positionFromBounds(newarkBounds).bearing((float)26.3248);
        GroundOverlay imageOverlay = mMap.addGroundOverlay(goo);
        mMap.addMarker(new MarkerOptions().position(bigC).title("Big C"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(bigC).zoom((float)19.5).bearing((float)-36.8).build();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bigC));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                float zoom= cameraPosition.zoom;
                if(zoom>20&&mCheckZoom==false) {
                    for (int i = 0; i < mListMarker.size(); i++) {
                        mListMarker.get(i).setVisible(true);
                    }
                    mCheckZoom=true;
                }
                else
                if(zoom<20&&mCheckZoom==true) {
                    for (int i = 0; i < mListMarker.size(); i++) {
                        mListMarker.get(i).setVisible(false);
                    }
                    mCheckZoom=false;
                }
            }
        });
    }

    private void setListMarker() {
        for (int i = 0; i < mNodesDisplay.size(); i++) {
            MarkerOptions options = new MarkerOptions()
                    .position(RealmRemote.getLocationFromName(mNodesDisplay.get(i).getName())).title(mNodesDisplay.get(i).getName());
            Marker marker = mMap.addMarker(options);
            mListMarker.add(marker);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DialogActivity.draw == 1) {
//            LatLng first = remote.getObjectPointFromName(DialogActivity.firstPoint);
//            LatLng second = remote.getObjectPointFromName(DialogActivity.secondPoint);
            LatLng first= RealmRemote.getLocationFromName(DialogActivity.firstPoint);
            LatLng second= RealmRemote.getLocationFromName(DialogActivity.secondPoint);
            Polyline polyline = mMap.addPolyline(new PolylineOptions().add(first, second).width(2));
            float results[] = new float[1];
            Location
                    .distanceBetween(first.latitude, first.longitude, second.latitude, second.longitude,
                            results);
//            remote.saveEdge(
//                    new Edge(DialogActivity.firstPoint, DialogActivity.secondPoint, results[0]));
//            remote.saveEdge(
//                    new Edge(DialogActivity.secondPoint, DialogActivity.firstPoint, results[0]));
            RealmRemote.saveEdge( new Edge(DialogActivity.firstPoint, DialogActivity.secondPoint, results[0]));
            RealmRemote.saveEdge( new Edge(DialogActivity.secondPoint, DialogActivity.firstPoint, results[0]));
            DialogActivity.draw = 0;
        }
        if (DialogActivity.draw == 2) {
            //  printRslt();
            if(mline!=null)
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
            Point vertexF = RealmRemote.getObjectPointFromName(DialogActivity.firstPoint);
            dijkstra.execute(vertexF);
            Point vertex1 = RealmRemote.getObjectPointFromName(DialogActivity.secondPoint);
            LinkedList<Point> path = dijkstra.getPath(vertex1);
            if (path!= null)
                for (int i = 0; i < path.size() - 1; i++) {
                    LatLng src = RealmRemote.getLocationFromName(path.get(i).getName());
                    LatLng dest = RealmRemote.getLocationFromName(path.get(i + 1).getName());

                    // mMap is the Map Object
                    mline = mMap.addPolyline(
                            new PolylineOptions().add(
                                    src, dest).width(2).color(Color.BLUE).geodesic(true)
                    );
                }
            else
                Toast.makeText(FloorActivity.this, "Không thể đến đây từ vị trí của bạn được "+mEdges.size(), Toast.LENGTH_LONG).show();
            DialogActivity.draw = 0;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
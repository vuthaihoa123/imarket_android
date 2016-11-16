package com.example.framgia.imarketandroid.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.model.CustomMarker;
import com.example.framgia.imarketandroid.data.model.Edge;
import com.example.framgia.imarketandroid.data.model.Graph;
import com.example.framgia.imarketandroid.data.model.Point;
import com.example.framgia.imarketandroid.data.remote.RealmRemote;
import com.example.framgia.imarketandroid.ui.activity.FloorActivity;
import com.example.framgia.imarketandroid.ui.views.CustomMarkerView;
import com.example.framgia.imarketandroid.util.algorithm.DijkstraAlgorithm;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;

/**
 * Created by framgia on 11/09/2016.
 */
public class MapUntils {
    public static void setDrawPath(Context context, Polyline mline, Point mCurrentLocation, Point
        mTargetLocation, List<Edge> mEdges, List<Point> mNodes, RealmList<Point> mVertexesHoa,
                                   RealmList<Edge> mEdgesHoa, GoogleMap mMap) {
        if (mline != null) mline.remove();
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
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph, context);
        Point vertexF = mCurrentLocation;
        dijkstra.execute(vertexF);
        Point vertex1 = mTargetLocation;
        LinkedList<Point> path = dijkstra.getPath(vertex1);
        if (path != null)
            for (int i = 0; i < path.size() - 1; i++) {
                LatLng src = RealmRemote.getLocationFromName(path.get(i).getId());
                LatLng dest = RealmRemote.getLocationFromName(path.get(i + 1).getId());
                mline = mMap.addPolyline(
                    new PolylineOptions().add(
                        src, dest).width(2).color(Color.BLUE).geodesic(true)
                );
            }
        else
            Toast.makeText(context, R.string.current_location_warning + mEdges.size
                (), Toast.LENGTH_LONG)
                .show();
        FloorActivity.sResumeValue = 0;
    }

    public static Bitmap createBitmapFromView(Context context, View view) {
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

    public static void slideLayoutRight(final View view, final Animation a, final Animation b) {
        new OnSwipeTouchListener(view).setOnSwipeListener(
            new OnSwipeTouchListener.onSwipeEvent() {
                @Override
                public void SwipeEventDetected(View v,
                                               OnSwipeTouchListener.SwipeTypeEnum SwipeType) {
                    if (SwipeType == OnSwipeTouchListener.SwipeTypeEnum
                        .RIGHT_TO_LEFT) {
                        view.startAnimation(a);
                        view.setVisibility(View.VISIBLE);
                    }
                    if (SwipeType == OnSwipeTouchListener.SwipeTypeEnum.LEFT_TO_RIGHT) {
                        view.startAnimation(b);
                        view.setVisibility(View.INVISIBLE);
                    }
                }
            });
    }

    public static void slideLayoutLeft(final View view, final Animation a, final Animation b) {
        new OnSwipeTouchListener(view).setOnSwipeListener(
            new OnSwipeTouchListener.onSwipeEvent() {
                @Override
                public void SwipeEventDetected(View v,
                                               OnSwipeTouchListener.SwipeTypeEnum SwipeType) {
                    if (SwipeType == OnSwipeTouchListener.SwipeTypeEnum.RIGHT_TO_LEFT) {
                        view.startAnimation(a);
                        view.setVisibility(View.INVISIBLE);
                    }
                    if (SwipeType == OnSwipeTouchListener.SwipeTypeEnum.LEFT_TO_RIGHT) {
                        view.startAnimation(b);
                        view.setVisibility(View.VISIBLE);
                    }
                }
            });
    }

    public static float calculateDistance(LatLng origin, Point source) {
        float distance[] = new float[1];
        Location.distanceBetween(origin.latitude, origin.longitude, source.getLat(), source.getLng
            (), distance);
        return distance[0];
    }

    public static float calculateDistance(Point origin, Point source) {
        float distance[] = new float[1];
        Location.distanceBetween(origin.getLat(), origin.getLng(), source.getLat(), source.getLng
            (), distance);
        return distance[0];
    }
    private void setDialogSavePoint(Context context, Dialog dialog){
        dialog= new Dialog(context);
        dialog.setContentView(R.layout.activity_save_point);
        dialog.setTitle("");

    }
}

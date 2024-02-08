package com.example.ex2evhodeimartinez;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MapaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapaFragment newInstance(String param1, String param2) {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private MapView mapa;
    IMapController controlMapa;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        //mapa jarri
        mapa = view.findViewById(R.id.mapaGPS);
        mapa.setTileSource(TileSourceFactory.MAPNIK);
        //Maparen haseira puntua ezarri
        controlMapa = mapa.getController();
        controlMapa.setZoom(15.65);
        GeoPoint startPoint = new GeoPoint(43.36562, -2.87443);
        controlMapa.setCenter(startPoint);
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        //Zooma jarri mapan
        mapa.setBuiltInZoomControls(true);
        mapa.setMultiTouchControls(true);

        //ElorrietaMarker
        Marker GatikaMarker = new Marker(mapa);
        GeoPoint markerElorrieta = new GeoPoint(43.36562, -2.87443);
        GatikaMarker.setPosition(markerElorrieta);
        GatikaMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        //marker imagen
        Drawable de = ResourcesCompat.getDrawable(getResources(), R.drawable.marker, null);
        Bitmap bitmapElorrieta = ((BitmapDrawable) de).getBitmap();
        Drawable dre = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmapElorrieta, (int) (15f * getResources().getDisplayMetrics().density), (int) (15 * getResources().getDisplayMetrics().density), true));
        GatikaMarker.setIcon(dre);
        GatikaMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                // Cambiar al fragmento InfoFragment
                InfoFragment infoFragment = new InfoFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentCont, infoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return false;
            }
        });
        mapa.getOverlays().add(GatikaMarker);
        return view;
    }
}
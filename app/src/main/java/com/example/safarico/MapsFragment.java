package com.example.safarico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    //dier
    Dier[] dieren = HomeFragment.dieren;
    String selected;
    //user


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION
                },100);
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


            for (Dier dier : dieren) {
                LatLng dierMarker = new LatLng(dier.getLatitude(), dier.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(dierMarker).title(dier.getNaam()));
            }

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    selected = marker.getTitle();
                    for (int i=0; i<dieren.length; i++) {
                        if (selected.equals(dieren[i].getNaam())) {
                            dieren[i].setSelected(true);
                            //call updateSelected() van parent
                        }else{
                            dieren[i].setSelected(false);
                        }
                    }
                    return false;
                }
            });

        }
    };

    public String getSelected() {
        return selected;
    }

    public void update () {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}
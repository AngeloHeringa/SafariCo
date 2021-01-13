package com.example.safarico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {
    //dier
    Dier[] dieren = HomeFragment.dieren;
    String selected;
    //userlocation
    Location myLocation;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION
                },100);
                myLocation = googleMap.getMyLocation();
            }
            googleMap.setMyLocationEnabled(true);
            CameraPosition cameraPosition;
            try {
                cameraPosition = new CameraPosition.Builder().target(new LatLng(myLocation.getLatitude(), myLocation.getLongitude())).zoom(10f).build();
            }catch(NullPointerException e){
                e.printStackTrace();
                cameraPosition = new CameraPosition.Builder().target(new LatLng(52.124064, 4.470375)).zoom(10.5f).build();
            }
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            final boolean[] done = {false};
            googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener(){
                @Override
                public void onMyLocationChange(Location location) {
                     if (!done[0]){
                        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(15f).build()
                        ));
                        done[0] = true;
                    }

                }
            });


            for (Dier dier : dieren) {
                LatLng dierMarker = new LatLng(dier.getLatitude(), dier.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(dierMarker).title(dier.getNaam()));
            }

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    selected = marker.getTitle();
                    for (Dier dier : dieren) {
                        if (selected.equals(dier.getNaam())) {
                            dier.setSelected(true);
                            googleMap.moveCamera(
                                CameraUpdateFactory.newCameraPosition(
                                        new CameraPosition.Builder().target(
                                                new LatLng(dier.getLatitude(),  dier.getLongitude())
                                        ).zoom(14.0f).build()
                                )
                            );

                            //call updateSelected() van parent
                        } else {
                            dier.setSelected(false);
                        }
                    }
                    return false;
                }
            });
        }
    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
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
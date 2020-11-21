package com.example.safarico;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment{

    //info
    TextView textNaam;
    TextView textAfstand;

    //popup
    private Button popupButton;
    private Dialog dialog;

    //Location
    LocationManager locationManager;
    LocationListener locationListener;
    Location userLocation;

    //map
    Dier dier;
    public static Dier[] dieren = {new Dier("olifant", 52.142845, 4.441977, false), new Dier("aap", 51.142845, 4.501977, false)};
    public double[] userLatLong;
//    mapsFragment;



    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog(View v, Dier dier) {
        dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.popup_window);
        //size fix voor dialog
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        final LinearLayout dierContainer = dialog.findViewById(R.id.dierContainer);
        final ImageView image = dialog.findViewById(R.id.dierFoto);
        final TextView text = dialog.findViewById(R.id.textNaamDialog);
        text.setText(dier.getNaam());
        Button sluitButton = dialog.findViewById(R.id.sluitButton);
        sluitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //kaart

        //dier voorbeeld
        dier = dieren[0];
        //diernaam textView
        textNaam = view.findViewById(R.id.textNaam);
        textNaam.setText(dier.getNaam());
        // afstand textView
        textAfstand = view.findViewById(R.id.textAfstand);
        //getLocation
        locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NotNull Location location) {
//                Toast.makeText(getActivity(), "mobile location is in listener"+location,Toast.LENGTH_SHORT);
                userLocation = location;
            }
        };
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
        getLocation();

        //calculate distance tussen userLocation en dier
        if (calculateDistance(dier, userLocation)!=0){
            textAfstand.setText(String.format("%.2f", calculateDistance(dier, userLocation))+" km");
        }else{
            textAfstand.setText("Locatie niet beschikbaar");
        }

        //popup button
        popupButton = view.findViewById(R.id.popupButton);
        popupButton.setOnClickListener(v -> showDialog(v, dier));

        //map
        Handler handler = new Handler();
        Runnable update = new Runnable(){
            @Override
            public void run() {
                updateSelected();
                handler.postDelayed(this, 100);
            }
        };
        update.run();

        return view;
    }

    //user permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //location
    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getParentFragment().getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locationListener);
            userLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            userLatLong = new double[] {userLocation.getLatitude(), userLocation.getLongitude()};
//            Toast.makeText(getActivity(), ""+userLocation.getLongitude()+", "+userLocation.getLatitude(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSelected(){
        for (int i=0; i<dieren.length; i++){
            if (dieren[i].isSelected()){
                textNaam.setText(dieren[i].getNaam());
                textAfstand.setText(String.format("%.2f",calculateDistance(dieren[i], userLocation))+" km");
                dier=dieren[i];
            }
        }
    }

    //calculate distance
    private double calculateDistance (Dier dier, Location userLocation) {
        try {
            double afstand = (Math.sin(Math.toRadians(dier.getLatitude())) *
                Math.sin(Math.toRadians(userLocation.getLatitude())) +
                Math.cos(Math.toRadians(dier.getLatitude())) *
                Math.cos(Math.toRadians(userLocation.getLatitude())) *
                Math.cos(Math.toRadians(dier.getLongitude() - userLocation.getLongitude())));

            return ((Math.toDegrees(Math.acos(afstand)) * 69.09 * 1.6093));
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
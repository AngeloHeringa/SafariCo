package com.example.safarico;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class HomeFragment extends Fragment{
    //info
    TextView textNaam;
    TextView textAfstand;

    //popup
    Dialog dialog;

    //runnable
    Handler handler;
    Runnable update;
    boolean active = true;

    //Location
    LocationManager locationManager;
    LocationListener locationListener;
    public static Location userLocation;

    //dieren uit database
    public static Dier[] dieren = MainActivity.dieren;

    Dier selectedDier = dieren[0];

    public static HomeFragment newInstance(String param1, String param2) {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //titel
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle(getResources().getString(R.string.app_name));
    }

    //stel popup in
    @SuppressLint("SetTextI18n")
    public void showDialog(View v, Dier dier, Resources res) {
        dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.popup_window);

        //size fix voor dialog
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);

        //inhoud
        TextView inhoud = dialog.findViewById(R.id.info);
        try {
            inhoud.setText(genereerInfo(selectedDier, inhoud));
        }catch(NullPointerException e){
            e.printStackTrace();
            inhoud.setText(selectedDier.getNaam()+" is geen bestaand Diersoort");
        }
        ImageView image = dialog.findViewById(R.id.dierFoto);
        try {
            image.setImageDrawable(ResourcesCompat.getDrawable(res, res.getIdentifier(dier.getNaam().toLowerCase(), "drawable", requireActivity().getPackageName()), res.newTheme()));
        }catch (Resources.NotFoundException e){
            e.printStackTrace();
        }
        TextView text = dialog.findViewById(R.id.textNaamDialog);
        text.setText(("Diersoort: "+dier.getNaam()));
        Button sluitButton = dialog.findViewById(R.id.sluitButton);
        sluitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dialog.dismiss();}
        });
        dialog.show();
    }

    //inhoud bij popup voor het geselecteerde dier
    private String genereerInfo(Dier dier, TextView t) {
        String dierNaam = dier.getNaam().toLowerCase();
        Diersoort[] diersoorten = MainActivity.diersoorten;
        StringBuilder oorzaakText = new StringBuilder();
        StringBuilder dierInfo = new StringBuilder();
        String output = "lege text";
        if (diersoorten != null) {
            for (Diersoort diersoort : diersoorten) {
                if (diersoort.getNaam().toLowerCase().equals(dierNaam)) {
                    //soort dier
                    String[] zoogdieren = {"olifant", "aap", "baviaan", "giraffe", "tijger", "neushoorn"};
                    for (String zoogdier : zoogdieren){
                        if (diersoort.getNaam().toLowerCase().equals(zoogdier)){
                            dierInfo.append("Dit diersoort is een zoogdier. ");
                        }
                    }
                    String[] vogels = {"penguin"};
                    for (String vogel : vogels){
                        if (diersoort.getNaam().toLowerCase().equals(vogel)){
                            dierInfo.append("Dit diersoort is een vogel. ");
                        }
                    }
                    String[] reptielen = {"krokodil"};
                    for (String reptiel : reptielen){
                        if (diersoort.getNaam().toLowerCase().equals(reptiel)){
                            dierInfo.append("Dit diersoort is een reptiel. ");
                        }
                    }

                    //eet gedrag
                    String[] herbivoren = {"olifant", "giraffe", "neushoorn"};
                    for (String herbivoor : herbivoren){
                        if (diersoort.getNaam().toLowerCase().equals(herbivoor)){
                            dierInfo.append("Omdat dit een herbivoor is eet het alleen planten.\n");
                        }
                    }
                    String[] carnivoren = {"tijger", "pernguin", "krokodil"};
                    for (String carnivoor : carnivoren){
                        if (diersoort.getNaam().toLowerCase().equals(carnivoor)){
                            dierInfo.append("Omdat dit een carnivoor is eet het alleen vlees.\n");
                        }
                    }
                    String[] omnivoren = {"aap", "gorilla", "baviaan"};
                    for (String omnivoor : omnivoren){
                        if (diersoort.getNaam().toLowerCase().equals(omnivoor)){
                            dierInfo.append("Omdat dit een omnivoor is eet het vlees en planten.\n");
                        }
                    }

                    //bedreiging info
                    for (String oorzaak : diersoort.getOorzaak().split(" ")) {
                        if (oorzaak.equals("jagers")) {
                            oorzaakText.append(oorzaakText.toString().equals("") ? ("De rede dat de " + dierNaam + " bedreigd is, is") : (" Ook wordt de " + dierNaam + " bedreigd")).append(" omdat er illegaal op wordt gejaagd door stropers.");
                        }
                        if (oorzaak.equals("vissers")) {
                            oorzaakText.append(oorzaakText.toString().equals("") ? ("De rede dat de " + dierNaam + " bedreigd is, is") : (" Ook wordt de " + dierNaam + " bedreigd")).append(" omdat er illegaal wordt gevist op dit diersoort.");
                        }
                        if (oorzaak.equals("broeikas")) {
                            oorzaakText.append(oorzaakText.toString().equals("") ? ("De rede dat de " + dierNaam + " bedreigd is, is") : (" Ook wordt de " + dierNaam + " bedreigd")).append(" door de degradatie van het leefgebied van dit diersoort door de opwarming van de aarde.");
                        }
                        if (oorzaak.equals("leefgebied")) {
                            oorzaakText.append(oorzaakText.toString().equals("") ? ("De rede dat de " + dierNaam + " bedreigd is, is") : (" Ook wordt de " + dierNaam + " bedreigd")).append(" omdat het leefgebied van dit diersoort aangetast wordt door mensen.");
                        }
                    }
                    output = (
                            dierInfo+
                            "De diersoort " + dierNaam +
                                    " is " + (diersoort.isBedreigd() ? "" : "niet ") + "bedreigd. " +
                                    "Er leven momenteel " + (diersoort.getCount() >= 1000000 ? diersoort.getCount() / 1000000 + " miljoen" : diersoort.getCount()) + " van dit diersoort op aarde.\n" +
                                    oorzaakText
                            //waarom bedreigd, oorzaak
                    );
                }
            }
        }
        return output;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //diernaam textView
        textNaam = view.findViewById(R.id.textNaam);
        textNaam.setText("Diersoort: "+selectedDier.getNaam());

        // afstand textView
        textAfstand = view.findViewById(R.id.textAfstand);

        //getUserLocation
        locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NotNull Location location) {
                userLocation = location;
                //select dichtijste dier
                updateDichtbij();
            }
        };
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            getLocation();
        }

        //calculate distance tussen userLocation en dier
        if (calculateDistance(selectedDier, userLocation)!=0){
            textAfstand.setText("Afstand: "+String.format("%.2f", calculateDistance(selectedDier, userLocation))+" km");
        }else{
            textAfstand.setText("Locatie niet beschikbaar");
        }

        //popup button
        Button popupButton = view.findViewById(R.id.popupButton);
        popupButton.setOnClickListener(v -> {
            showDialog(v, selectedDier, getResources());
        });

        //achtergrond process voor updates van welk dier is geselecteerd
        Handler handler = new Handler();
        Runnable update = new Runnable(){
            @Override
            public void run() {
                if (active){
                    updateSelected();
                }
                handler.postDelayed(this, 100);
            }
        };
        update.run();

        return view;
    }

    @Override
    public void onPause() {
        active = false;
        super.onPause();
    }

    @Override
    public void onResume() {
        active = true;
        super.onResume();
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
            locationManager = (LocationManager) requireActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locationListener);
            userLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //update welk dier is geselecteerd
    @SuppressLint("DefaultLocale")
    public void updateSelected(){
        for (Dier value : dieren) {
            if (value.isSelected()) {
                textNaam.setText(("Diersoort: " + value.getNaam()));
                if (calculateDistance(selectedDier, userLocation)>1) {
                    textAfstand.setText(String.format("Afstand: %.2f km", calculateDistance(value, userLocation)));
                }else if(calculateDistance(selectedDier, userLocation)!=0){
                    textAfstand.setText(String.format("Afstand: %3.0f meter", (calculateDistance(value, userLocation)*1000)));
                }
                selectedDier = value;
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
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    //selecteer dichtbije dier
    private void updateDichtbij(){
        selectedDier.setSelected(false);
        double afstand = 0;
        for (Dier dier : dieren){
            if (calculateDistance(dier, userLocation) < afstand){
                afstand = calculateDistance(dier, userLocation);
                selectedDier = dier;
            }
        }
        selectedDier.setSelected(true);
    }
}
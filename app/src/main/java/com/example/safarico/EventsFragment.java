package com.example.safarico;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Objects;

public class EventsFragment extends Fragment {

    //voorbeeld eventList, uiteindelijk importeren uit DB
    Event[] eventLijst= {new Event("dierentuin1", "leeuwen voeren", Calendar.getInstance().getTime()), new Event("dierentuin1", "apen voeren", Calendar.getInstance().getTime())};


    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance(String param1, String param2) {
        return new EventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //titel
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle(getResources().getString(R.string.app_name));

    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        //titel tekst
        TextView titel = view.findViewById(R.id.parkNaam);

        //tijdelijke naam maar uiteindelijk moet hij de dichtsbijzijnde vinden uit de DB vergeleken met de locatie
        String huidigePark = "dierentuin1";
        titel.setText(("Agenda voor "+huidigePark));

        //alle inputs van overeenkomende park ( event.getPark().equals(huidigePark) )
        int i=0;

        for (Event event: eventLijst){
            if ((Calendar.getInstance().getTime().getDate() == (event.getTijd().getDate()))&& event.getPark().equals(huidigePark)){
                LinearLayout lijst = view.findViewById(R.id.eventLijst);
                final TextView input = new TextView(view.getContext());
                i++;
                if (i%2==0){
                    input.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));
                }
                else{
                    input.setBackgroundColor(this.getResources().getColor(R.color.colorAccent2));
                }
                input.setTextSize(25);
                input.setText((" "+(String.format("%02d",event.getTijd().getHours())+":"+String.format("%02d",(event.getTijd().getMinutes()))+"  "+event.getOmschrijving())));
                lijst.addView(input);
            }
        }

        return view;
    }
}
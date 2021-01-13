package com.example.safarico;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Objects;

public class EventsFragment extends Fragment {

    //voorbeeld eventList, uiteindelijk importeren uit DB
    Event[] eventLijst = MainActivity.eventLijst;

    Dier[] dieren = HomeFragment.dieren;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        //titel tekst
        TextView titel = view.findViewById(R.id.parkNaam);
        //tijdelijke naam maar uiteindelijk moet hij de dichtsbijzijnde vinden uit de DB vergeleken met de locatie
        String huidigePark = getSelectedPark();
        titel.setText(("Agenda voor "+huidigePark));
        //searchBar
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String query = String.valueOf(searchView.getQuery());
                ViewGroup viewGroup = (view.findViewById(R.id.eventLijst));
                for(int i = 0; i < ((ViewGroup) viewGroup).getChildCount(); i++) {
                    View nextChild = ((ViewGroup) viewGroup).getChildAt(i);
                    nextChild.setVisibility(View.GONE);
                }
                updateoutput(view, query, huidigePark);
                return false;
            }
        });
        updateoutput(view, "", huidigePark);

        return view;
    }

    @SuppressLint("DefaultLocale")
    private void updateoutput(View view, String query, String huidigePark){
        //alle inputs van overeenkomende park ( event.getPark().equals(huidigePark) )
        int i=0;

        if (eventLijst != null) {
            for (Event event : eventLijst) {
                if (!(Calendar.getInstance().getTime().getDate() == (event.getTijd().getDate())) && event.getPark().equals(huidigePark)) {
                    LinearLayout lijst = view.findViewById(R.id.eventLijst);
                    final TextView output = new TextView(view.getContext());
                    if (query.toLowerCase().equals("") || event.getDiersoort().toLowerCase().startsWith(query)) {
                        output.setVisibility(View.VISIBLE);
                    } else {
                        output.setVisibility(View.GONE);
                    }
                    i++;
                    if (i % 2 == 0) {
                        output.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));
                    } else {
                        output.setBackgroundColor(this.getResources().getColor(R.color.colorAccent2));
                    }
                    output.setTextSize(25);
                    output.setText((" " + (String.format("%02d", event.getTijd().getHours()) + ":" + String.format("%02d", (event.getTijd().getMinutes())) + "  " + event.getOmschrijving())));
                    lijst.addView(output);
                }
            }
        }
    }

    //return selected dier
    private String getSelectedPark(){
        String naam = "";
        for (Dier dier : dieren){
            if (dier.isSelected()){
                naam = dier.getLocatieNaam();
            }
        }
        return naam;
    }

}
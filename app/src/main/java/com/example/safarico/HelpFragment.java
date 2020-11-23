package com.example.safarico;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

public class HelpFragment extends Fragment {

    public HelpFragment() {
        // Required empty public constructor
    }

    public static HelpFragment newInstance(String param1, String param2) {
        return new HelpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //titel
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle(getResources().getString(R.string.app_name));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        requireActivity().setTitle(getResources().getString(R.string.app_name));
        //https://whatcanyoudo.earth/tag/sdg-15/ bron
        TextView tv = (TextView) view.findViewById(R.id.bronLink);
        TextView tv1 = (TextView) view.findViewById(R.id.watDoen);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv1.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }
}
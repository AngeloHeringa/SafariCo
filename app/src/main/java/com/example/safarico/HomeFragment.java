package com.example.safarico;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    private Button popupButton;
    private Dialog dialog;

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

    public void showDialog(View v, Dier dier){
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //dier voorbeeld
        Dier dier = new Dier("olifant", 52.142845, 4.441977);
        //diernaam vak
        TextView textNaam = view.findViewById(R.id.textNaam);
        textNaam.setText(dier.getNaam());
        //popup button
        popupButton =(Button) view.findViewById(R.id.popupButton);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v, dier);
            }
        });
        return view;
    }
}
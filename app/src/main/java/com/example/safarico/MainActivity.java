package com.example.safarico;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import kotlin.Suppress;

public class MainActivity extends AppCompatActivity {
    // later de arrays uit de database halen:
    // voorbeeld dieren
    public static Dier[] dieren = {new Dier("penguin", 52.148845, 4.440977, false, "dierentuin1"), new Dier("olifant", 52.142845, 4.441977, false, "dierentuin1"), new Dier("aap", 52.202845, 4.501977, false, "dierentuin2")};
    // voorbeeld events
    public static Event[] eventLijst= {new Event("dierentuin1", "penguins voeren", Calendar.getInstance().getTime(), "penguin"), new Event("dierentuin1", "olifanten voeren", Calendar.getInstance().getTime(), "olifant"), new Event("dierentuin2", "apen voeren", Calendar.getInstance().getTime(), "aap")};
    //diersoorten voorbeeld
    public static Diersoort[] diersoorten = {new Diersoort("penguin", 10000000,true, "vissers broeikas"),new Diersoort("olifant", 500000, true,"jagers"), new Diersoort("aap", 1000000, true,"jagers broeikas leefgebied")};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get database variables
        try {
            new Task().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("int","interrupt");
            e.printStackTrace();
        }

        //activate fragments
        setContentView(R.layout.activity_main);

        //navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.eventsFragment, R.id.helpFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @SuppressWarnings("deprecation")
    public static class Task extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //Database connectie
            Log.e("TAG", "background Task");
            try {
                DatabaseConnection.databaseConnection();
                dieren = DatabaseConnection.dierenLijst;
                diersoorten = DatabaseConnection.diersoortLijst;
                eventLijst = DatabaseConnection.eventLijst;
            } catch (InstantiationException ex) {

                ex.printStackTrace();
            } catch (SQLException ex) {

                ex.printStackTrace();
            } catch (IllegalAccessException ex) {

                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {

                ex.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }

            return null;
        }

    }
}
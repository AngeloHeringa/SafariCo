package com.example.safarico;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DatabaseConnection {
    static Connection connection = null;
    static String databaseName = "sql7386153";
    static String url = "jdbc:mysql://52.29.239.198:3306/" + databaseName;

    static String username = "sql7386153";
    static String password = "2CSLdvT76a";

    public static Dier[] dierenLijst;
    public static Diersoort[] diersoortLijst;
    public static Event[] eventLijst;

    public static void databaseConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);

        updateDieren(connection);
        updateDiersoorten(connection);
        updateEvents(connection);

    }

    private static void updateDieren(Connection connection){
        try {
            //export dieren
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Dieren");

            rs.last();
            int size = rs.getRow();
            Dier[] dieren = new Dier[size];
            Log.e("tag", "new Dier[" + size + "]");
            rs.first();
            String soort = rs.getString("Soort");
            double longt = rs.getDouble("Y_Coord");
            double lat = rs.getDouble("X_Coord");
            String locatie = rs.getString("Locatie");
            dieren[rs.getRow() - 1] = new Dier(soort, lat, longt, false, locatie);
            while (rs.next()) {
                soort = rs.getString("Soort");
                longt = rs.getDouble("Y_Coord");
                lat = rs.getDouble("X_Coord");
                locatie = rs.getString("Locatie");
                dieren[rs.getRow() - 1] = new Dier(soort, lat, longt, false, locatie);
            }
            dierenLijst = dieren;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void updateDiersoorten(Connection connection){
        try {
            //export diersoorten
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Diersoort");
            rs.last();
            int size = rs.getRow();
            Diersoort[] diersoorten = new Diersoort[size];
            Log.e("tag", "new Diersoort["+size+"];");
            rs.first();
            String dierSoort = rs.getString("Soort");
            int counter = rs.getInt("Counter");
            boolean bedreigd = rs.getBoolean("Uitsterven");
            String oorzaak = rs.getString("Oorzaak");
            diersoorten[rs.getRow()-1] = new Diersoort(dierSoort, counter, bedreigd, oorzaak);
            while (rs.next()) {
                dierSoort = rs.getString("Soort");
                counter = rs.getInt("Counter");
                bedreigd = rs.getBoolean("Uitsterven");
                oorzaak = rs.getString("Oorzaak");
                diersoorten[rs.getRow()-1] = new Diersoort(dierSoort, counter, bedreigd, oorzaak);
            }
            diersoortLijst = diersoorten;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void updateEvents(Connection connection){
        try {
            //export events
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Events");
            rs.last();
            int size = rs.getRow();
            Event[] events = new Event[size];
            Log.e("tag", "new Event["+size+"];");
            rs.first();
            String park = rs.getString("Locatie");
            String omschrijving = rs.getString("Naam");
            Log.e("log",""+rs.getDate("Datum"));
            Log.e("log",""+rs.getTime("Begin_tijd"));

            Date tijd = new Date(
                    Integer.parseInt(String.valueOf(rs.getDate("Datum")).split("-")[0]),
                    Integer.parseInt(String.valueOf(rs.getDate("Datum")).split("-")[1]),
                    Integer.parseInt(String.valueOf(rs.getDate("Datum")).split("-")[2]),
                    Integer.parseInt(String.valueOf(rs.getTime("Begin_tijd")).split(":")[0]),
                    Integer.parseInt(String.valueOf(rs.getTime("Begin_tijd")).split(":")[1]),
                    Integer.parseInt(String.valueOf(rs.getTime("Begin_tijd")).split(":")[2])
            );

            String diersoort = rs.getString("Diersoort");
            events[rs.getRow()-1] = new Event(park, omschrijving, tijd, diersoort);
            while (rs.next()) {
                park = rs.getString("Locatie");
                omschrijving = rs.getString("Naam");
                tijd = new Date(
                    Integer.parseInt(String.valueOf(rs.getDate("Datum")).split("-")[0]),
                    Integer.parseInt(String.valueOf(rs.getDate("Datum")).split("-")[1]),
                    Integer.parseInt(String.valueOf(rs.getDate("Datum")).split("-")[2]),
                    Integer.parseInt(String.valueOf(rs.getTime("Begin_tijd")).split(":")[0]),
                    Integer.parseInt(String.valueOf(rs.getTime("Begin_tijd")).split(":")[1]),
                    Integer.parseInt(String.valueOf(rs.getTime("Begin_tijd")).split(":")[2])
                );
                diersoort = rs.getString("Diersoort");
                events[rs.getRow()-1] = new Event(park, omschrijving, tijd, diersoort);
            }
            eventLijst = events;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
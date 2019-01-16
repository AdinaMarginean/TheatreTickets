package com.example.adina.theatretickets.DataBase;

import android.content.Context;

import com.example.adina.theatretickets.DataBase.TheatreTicketsDatabase;

public class ConnectDatabase {
    public static TheatreTicketsDatabase theatreTicketsDatabase;

    public static void getConnection(Context context){
        theatreTicketsDatabase = TheatreTicketsDatabase.getDatabase(context);
    }

    public static TheatreTicketsDatabase getTheatreTicketsDatabase() {
        return theatreTicketsDatabase;
    }
}

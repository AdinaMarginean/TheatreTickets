package com.example.adina.theatretickets.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.adina.theatretickets.Utils.Converters;
import com.example.adina.theatretickets.Models.Play;
import com.example.adina.theatretickets.Models.Ticket;
import com.example.adina.theatretickets.Models.User;

@Database(entities = {Play.class, User.class, Ticket.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TheatreTicketsDatabase extends RoomDatabase {
    public abstract Dao daoAccess() ;

    private static volatile TheatreTicketsDatabase INSTANCE;

    public static TheatreTicketsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TheatreTicketsDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TheatreTicketsDatabase.class, "theatre_database").build();
                }
            }
        }
        return INSTANCE;
    }

}

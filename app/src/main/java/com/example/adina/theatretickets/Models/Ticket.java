package com.example.adina.theatretickets.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(indices = {@Index(value = {"seat", "playID"}, unique = true)})
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long ticketID;
    @ForeignKey(entity = Play.class, parentColumns = "playID", childColumns = "playID")
    private long playID;

    @ForeignKey(entity = User.class, parentColumns = "userID", childColumns = "userID")
    private long userID;
    private int seat;

    @NonNull
    public long getTicketID() {
        return ticketID;
    }

    public void setTicketID(@NonNull long ticketID) {
        this.ticketID = ticketID;
    }



    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }


    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public long getPlayID() {
        return playID;
    }

    public void setPlayID(long playID) {
        this.playID = playID;
    }


}

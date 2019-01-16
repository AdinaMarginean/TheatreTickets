package com.example.adina.theatretickets.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Date;


@Entity(indices = {@Index(value = {"title"}, unique = true)})
public class Play {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long playID;
    private String title;
    private String details;
    private Date playDate;

    private int availableTickets;

    public long getPlayID() {
        return playID;
    }

    public void setPlayID(long playID) {
        this.playID = playID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getPlayDate() {
        return playDate;
    }

    public void setPlayDate(Date playDate) {
        this.playDate = playDate;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
}

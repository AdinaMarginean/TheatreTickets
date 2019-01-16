package com.example.adina.theatretickets.Models.Builders;
import com.example.adina.theatretickets.Models.Play;

import java.sql.Date;


public class PlayBuilder {
    private Play play;

    public PlayBuilder(){
        play = new Play();
    }

    public PlayBuilder setId(long id){
        play.setPlayID(id);
        return this;
    }

    public PlayBuilder setTitle(String title){
        play.setTitle(title);
        return this;
    }

    public PlayBuilder setDetails(String details){
        play.setDetails(details);
        return this;
    }

    public PlayBuilder setPlayDate(Date date){
        play.setPlayDate(date);
        return this;
    }

    public PlayBuilder setSeats(int nr){
        play.setAvailableTickets(nr);
        return this;
    }

    public Play build(){
        return play;
    }
}

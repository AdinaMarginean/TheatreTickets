package com.example.adina.theatretickets.Models.Builders;

import com.example.adina.theatretickets.Models.Ticket;

public class TicketBuilder {
    private Ticket ticket;

    public TicketBuilder(){
        ticket = new Ticket();
    }


    public TicketBuilder setPlayID(long playID) {
        ticket.setPlayID(playID);
        return this;
    }

    public TicketBuilder setUserID(long userID) {
        ticket.setUserID(userID);
        return this;
    }

    public TicketBuilder setSeat(int seat) {
        ticket.setSeat(seat);
        return this;
    }
    public Ticket build() {return ticket;}
}

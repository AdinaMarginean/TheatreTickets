package com.example.adina.theatretickets.DataBase;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.adina.theatretickets.Models.Play;
import com.example.adina.theatretickets.Models.Ticket;
import com.example.adina.theatretickets.Models.User;

import java.sql.Date;
import java.util.List;

@android.arch.persistence.room.Dao
public interface Dao {

    //Piese de teatru
    @Insert
    void insertPlay(Play play);

    @Query("SELECT availableTickets FROM Play WHERE playID = :playID")
    int getAvailableTickets(long playID);

    @Query("UPDATE Play SET availableTickets = :availableTickets WHERE playID = :playID")
    void updatePlayWithSeats(int availableTickets, long playID);

    @Query("SELECT * FROM Play WHERE playDate = :playDate")
    Play returnPlayByDate(Date playDate);

    @Query("SELECT * FROM Play WHERE playID = :playID")
    Play returnPlayByID(long playID);

    @Query("SELECT * FROM Play")
    List<Play> getAllPlays();

    @Query("UPDATE Play SET availableTickets = :value1 WHERE playID = :id1")
    int updateTickets(int value1, int id1);

    @Update
    void updatePlay(Play play);

    @Delete
    void deletePlay(Play play);

    //Utilizatori
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM User WHERE userID = :id")
    User returnUserByID(long id);

    @Query("SELECT * FROM User WHERE username = :username")
    User returnUserByUsername(String username);

    @Query("SELECT * FROM USER")
    List<User> getUsers();

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    //Bilete
    @Insert
    void insertTicket(Ticket ticket);

    @Query("SELECT * FROM Ticket WHERE playID = :playID")
    Ticket returnTicketByPlayID(long playID);


    @Query("SELECT * FROM Ticket WHERE ticketID = :ticketID")
    Ticket returnTicketByID(long ticketID);

    @Update
    void updateTicket(Ticket ticket);

    @Delete
    void deleteTicket(Ticket ticket);

    @Query("SELECT * FROM TICKET WHERE userID = :userID")
    List<Ticket> getTickets(long userID);

    @Query("SELECT * FROM TICKET")
    List<Ticket> getALLTickets();


}

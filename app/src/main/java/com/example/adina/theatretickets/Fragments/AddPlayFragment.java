package com.example.adina.theatretickets.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Script;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.adina.theatretickets.DataBase.ConnectDatabase;
import com.example.adina.theatretickets.LoginActivity;
import com.example.adina.theatretickets.Models.Builders.PlayBuilder;
import com.example.adina.theatretickets.Models.Play;
import com.example.adina.theatretickets.R;

import java.util.Calendar;
import java.util.Date;

public class AddPlayFragment extends Fragment {
    private ScrollView scrollView;
    private EditText titleText;
    private EditText detailsText;
    private DatePicker datePicker;
    private EditText seatsText;
    private Button addButton;

    private String mTitle;
    private String mDetails;
    private String mDate;
    private int mSeats;
    private Date date;

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_admin_add_play, viewGroup, false);
        scrollView = view.findViewById(R.id.scrollView);
        titleText = view.findViewById(R.id.editTitle);
        detailsText = view.findViewById(R.id.editDetails);
        datePicker = view.findViewById(R.id.datePicker);
        seatsText = view.findViewById(R.id.editSeats);
        addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFromUI();
                try{
                    insertPlay();
                    getActivity().getSupportFragmentManager().popBackStack();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }

    public void getTextFromUI(){
        mTitle = titleText.getText().toString();
        mDetails = detailsText.getText().toString();
        mSeats = Integer.parseInt(seatsText.getText().toString());

    }

    public void insertPlay(){
        final Play play = new PlayBuilder().setTitle(mTitle)
                .setDetails(mDetails)
                .setPlayDate(getDateFromDatePicker(datePicker))
                .setSeats(mSeats)
                .build();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ConnectDatabase.getTheatreTicketsDatabase().daoAccess().insertPlay(play);
            }
        });
    }

    public java.sql.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear()-1900;

        return new java.sql.Date(year, month, day);

    }
}

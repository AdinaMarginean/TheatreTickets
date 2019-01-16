package com.example.adina.theatretickets.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.adina.theatretickets.Models.Builders.PlayBuilder;
import com.example.adina.theatretickets.Models.Play;
import com.example.adina.theatretickets.R;

import java.util.Date;

public class EditPlayFragment extends Fragment {
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
    private int position;
    private Play play;

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        position = getArguments().getInt("position");

        play = ListAllPlaysFragment.playList.get(position);

        View view = inflater.inflate(R.layout.activity_edit_play, viewGroup, false);
        scrollView = view.findViewById(R.id.scrollViewEdit);
        titleText = view.findViewById(R.id.editTitleEdit);
        detailsText = view.findViewById(R.id.editDetailsEdit);
        datePicker = view.findViewById(R.id.datePickerEdit);
        seatsText = view.findViewById(R.id.editSeatsEdit);
        addButton = view.findViewById(R.id.addButtonEdit);

        titleText.setText(play.getTitle());
        detailsText.setText(play.getDetails());
        seatsText.setText(String.valueOf(play.getAvailableTickets()));
        seatsText.setEnabled(false);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFromUI();
                try{
                    updatePlay();
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

    public void updatePlay(){
        final Play p = new PlayBuilder().setId(play.getPlayID())
                .setTitle(mTitle)
                .setDetails(mDetails)
                .setPlayDate(getDateFromDatePicker(datePicker))
                .setSeats(mSeats)
                .build();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ConnectDatabase.getTheatreTicketsDatabase().daoAccess().updatePlay(p);
            }
        });
    }

    public java.sql.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        return new java.sql.Date(year, month, day);

    }
}

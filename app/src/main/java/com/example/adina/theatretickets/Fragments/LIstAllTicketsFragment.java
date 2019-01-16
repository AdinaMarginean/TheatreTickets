package com.example.adina.theatretickets.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adina.theatretickets.Adapters.TicketsAdapter;
import com.example.adina.theatretickets.Adapters.UserAdapter;
import com.example.adina.theatretickets.DataBase.ConnectDatabase;
import com.example.adina.theatretickets.LoginActivity;
import com.example.adina.theatretickets.Models.Play;
import com.example.adina.theatretickets.Models.Ticket;
import com.example.adina.theatretickets.Models.User;
import com.example.adina.theatretickets.R;
import com.example.adina.theatretickets.Utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class LIstAllTicketsFragment extends Fragment {

    private List<Ticket> ticketsList = new ArrayList<>();
    private List<Play> playList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TicketsAdapter mAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        preparePlaysList();
        prepareTicketsList();

        try {
            Thread.sleep(500);
        } catch (Exception e) {

        }

        View view = inflater.inflate(R.layout.activity_list_all_tickets, viewGroup, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new TicketsAdapter(playList, ticketsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.notifyDataSetChanged();

        return view;

    }

    private void prepareTicketsList() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ticketsList = ConnectDatabase.getTheatreTicketsDatabase().daoAccess().getTickets(LoginActivity.userID);
            }
        });
    }

    private void preparePlaysList() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                playList = ConnectDatabase.getTheatreTicketsDatabase().daoAccess().getAllPlays();
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}

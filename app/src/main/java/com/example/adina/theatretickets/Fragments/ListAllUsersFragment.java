package com.example.adina.theatretickets.Fragments;

import android.app.ProgressDialog;
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

import com.example.adina.theatretickets.AdminFirstActivity;
import com.example.adina.theatretickets.DataBase.ConnectDatabase;
import com.example.adina.theatretickets.Models.User;
import com.example.adina.theatretickets.R;
import com.example.adina.theatretickets.Utils.RecyclerTouchListener;
import com.example.adina.theatretickets.Adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListAllUsersFragment extends Fragment {

    private List<User> usersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        prepareUsersList();

        try {
            Thread.sleep(500);
        } catch (Exception e) {

        }
        View view = inflater.inflate(R.layout.activity_list_all_users, viewGroup, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        mAdapter = new UserAdapter(usersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();


        return view;

    }

    private void prepareUsersList() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                usersList = ConnectDatabase.getTheatreTicketsDatabase().daoAccess().getUsers();
            }
        });
    }

}

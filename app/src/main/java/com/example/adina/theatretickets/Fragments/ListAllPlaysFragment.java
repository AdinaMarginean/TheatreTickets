package com.example.adina.theatretickets.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.adina.theatretickets.AdminFirstActivity;
import com.example.adina.theatretickets.DataBase.ConnectDatabase;
import com.example.adina.theatretickets.LoginActivity;
import com.example.adina.theatretickets.Models.Builders.TicketBuilder;
import com.example.adina.theatretickets.Models.Play;
import com.example.adina.theatretickets.Adapters.PlayAdapter;
import com.example.adina.theatretickets.Models.Ticket;
import com.example.adina.theatretickets.R;
import com.example.adina.theatretickets.Utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class ListAllPlaysFragment extends Fragment {

    public static List<Play> playList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PlayAdapter mAdapter;

    private Context mContext;
    RelativeLayout mRelativeLayout;
    private Button mButtonAdd;

    private RecyclerView.LayoutManager mLayoutManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        preparePlaysList();

        try {
            Thread.sleep(500);
        } catch (Exception e) {

        }

        View view = inflater.inflate(R.layout.activity_list_all_plays, viewGroup, false);


        mContext = getContext();
        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.relLayout);
        mButtonAdd = (Button) view.findViewById(R.id.btn_add);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PlayAdapter(mContext, playList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if(AdminFirstActivity.isAdmin) {

            mButtonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment fragment = null;
                    Class fragmentClass = null;
                    fragmentClass = AddPlayFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();


                    mAdapter.notifyDataSetChanged();
                }
            });
        } else {
            mButtonAdd.setVisibility(View.INVISIBLE);
        }


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Play play = playList.get(position);
                if (play.getAvailableTickets() > 0){
                    showAlertDialog(position);
                    Toast.makeText(getContext(), play.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(),  "Sorry, there are no more tickets available !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onLongClick(View view, int position) {

                if (AdminFirstActivity.isAdmin) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);

                    Fragment fragment = null;
                    Class fragmentClass = EditPlayFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
                }
            }
        }));

        mAdapter.notifyDataSetChanged();

        return view;

    }



    private void preparePlaysList() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                playList = ConnectDatabase.getTheatreTicketsDatabase().daoAccess().getAllPlays();
            }
        });
    }

    public void showAlertDialog(final int position){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.buy_tickets);
        builder.setMessage(R.string.accept_to_buy);

        builder.setPositiveButton(R.string.buy, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        int availableTickets = ConnectDatabase.getTheatreTicketsDatabase().daoAccess().getAvailableTickets(playList.get(position).getPlayID());
                        if (availableTickets > 0) {
                            Ticket ticket = new TicketBuilder().setUserID(LoginActivity.userID).setPlayID(playList.get(position).getPlayID()).setSeat(availableTickets).build();
                            ConnectDatabase.getTheatreTicketsDatabase().daoAccess().insertTicket(ticket);
                            --availableTickets;
                            ConnectDatabase.getTheatreTicketsDatabase().daoAccess().updatePlayWithSeats(availableTickets, playList.get(position).getPlayID());

                        } else {


                        }

                    }
                });
                getActivity().getSupportFragmentManager().beginTransaction().detach(ListAllPlaysFragment.this).commit();
                getActivity().getSupportFragmentManager().beginTransaction().attach(ListAllPlaysFragment.this).commit();
                mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        LayoutInflater inflater = getActivity().getLayoutInflater();


        AlertDialog dialog = builder.create();
        dialog.show();
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

package com.example.adina.theatretickets.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adina.theatretickets.AdminFirstActivity;
import com.example.adina.theatretickets.DataBase.ConnectDatabase;
import com.example.adina.theatretickets.Models.Play;
import com.example.adina.theatretickets.R;

import java.util.List;

public class PlayAdapter extends RecyclerView.Adapter<PlayAdapter.MyViewHolder> {

    private List<Play> playList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, details, availableTickets;
        public ImageButton mRemoveButton;
        public RelativeLayout mRelativeLayout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            details = (TextView) view.findViewById(R.id.details);
            availableTickets = (TextView) view.findViewById(R.id.availableTickets);
            mRelativeLayout = view.findViewById(R.id.relRow);
            mRemoveButton = view.findViewById(R.id.remove);
        }
    }


    public PlayAdapter(Context context, List<Play> playList) {
        this.playList = playList;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.play_row_item, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Play play = playList.get(position);
        holder.title.setText(play.getTitle());
        holder.details.setText(play.getDetails());
        if (play.getAvailableTickets() > 0 ) {
            holder.availableTickets.setText(String.valueOf(play.getAvailableTickets()));
        } else {
            holder.availableTickets.setText("SOLD OUT !");
        }
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit
            }
        });

        if (AdminFirstActivity.isAdmin) {
            holder.mRemoveButton.setVisibility(View.VISIBLE);
        }else{
            holder.mRemoveButton.setVisibility(View.INVISIBLE);
        }

        holder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = playList.get(position).getTitle();
                playList.remove(position);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        ConnectDatabase.getTheatreTicketsDatabase().daoAccess().deletePlay(play);
                    }
                });
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, playList.size());
                Toast.makeText(mContext, "Removed: " + title, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return playList.size();
    }
}
package com.example.adina.theatretickets.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adina.theatretickets.Models.Play;
import com.example.adina.theatretickets.Models.Ticket;
import com.example.adina.theatretickets.R;

import java.util.List;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.MyViewHolder> {

    private List<Ticket> playTickets;
    private List<Play> playList;

    public TicketsAdapter(List<Play> playList, List<Ticket> ticketList) {
        this.playTickets = ticketList;
        this.playList = playList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView playTitle, seat, playDate;
        public RelativeLayout mRelativeLayout;
        public ImageButton mRemoveButton;


        public MyViewHolder(View view) {
            super(view);
            playTitle = (TextView) view.findViewById(R.id.playTitle);
            seat = (TextView) view.findViewById(R.id.seat);
            playDate = (TextView) view.findViewById(R.id.playDate);
            mRelativeLayout = view.findViewById(R.id.relRow);
            mRemoveButton = view.findViewById(R.id.remove);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ticket_row_item, viewGroup, false);

        TicketsAdapter.MyViewHolder viewHolder = new TicketsAdapter.MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final Ticket ticket = playTickets.get(i);
        myViewHolder.seat.setText(String.valueOf(ticket.getSeat()));
        for(Play p : playList) {
            if (p.getPlayID() == ticket.getPlayID()) {
                myViewHolder.playTitle.setText(p.getTitle());
                myViewHolder.playDate.setText(p.getPlayDate().toString());
            }
        }



    }

    @Override
    public int getItemCount() {
        return playTickets.size();
    }




//    @NonNull
//    @Override
//    public TicketsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.ticket_row_item, parent, false);
//
//        TicketsAdapter.MyViewHolder viewHolder = new TicketsAdapter.MyViewHolder(itemView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TicketsAdapter.MyViewHolder holder, int position) {
//        Ticket ticket = playTickets.get(position);
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//               Play play = ConnectDatabase.getTheatreTicketsDatabase().daoAccess().returnPlayByID(ticket.getPlayID());
//                holder.playTitle.setText(play.getTitle());
//                holder.seat.setText(ticket.getSeat());
//                holder.playDate.setText(play.getPlayDate().toString());
//
//
//            }
//
//        });
//
//        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //edit
//            }
//        });
//
//        holder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String title = playList.get(position).getTitle();
//                playList.remove(position);
//                AsyncTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        ConnectDatabase.getTheatreTicketsDatabase().daoAccess().deletePlay(play);
//                    }
//                });
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, playList.size());
//                Toast.makeText(mContext, "Removed: " + title, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return playList.size();
//    }
//
}

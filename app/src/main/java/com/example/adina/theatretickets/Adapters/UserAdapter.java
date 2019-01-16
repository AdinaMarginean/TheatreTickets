package com.example.adina.theatretickets.Adapters;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.adina.theatretickets.DataBase.ConnectDatabase;
import com.example.adina.theatretickets.Models.User;
import com.example.adina.theatretickets.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> usersList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userID, username, role;
        public ImageButton mRemoveButton;


        public MyViewHolder(View view) {
            super(view);
            userID = (TextView) view.findViewById(R.id.userID);
            username = (TextView) view.findViewById(R.id.username);
            role = (TextView) view.findViewById(R.id.userRole);
            mRemoveButton = view.findViewById(R.id.removeUser);

        }
    }

    public UserAdapter(List<User> usersList) {
        this.usersList = usersList;
    }

    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item, parent, false);

        return new UserAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserAdapter.MyViewHolder holder, final int position) {
        final User user = usersList.get(position);
        holder.userID.setText(String.valueOf(user.getUserID()));
        holder.username.setText(user.getUsername());
        holder.role.setText(String.valueOf(user.getRole()));

        holder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usersList.get(position).getUsername();
                usersList.remove(position);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        ConnectDatabase.getTheatreTicketsDatabase().daoAccess().deleteUser(user);
                    }
                });
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, usersList.size());
                //Toast.makeText(, "Removed: " + username, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}

package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 12/3/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UserViewHolder extends RecyclerView.ViewHolder {


    public TextView nameView;
    public TextView emailView;

    public UserViewHolder(View itemView) {
        super(itemView);

        nameView = itemView.findViewById(R.id.field_full_name_view);
        emailView = itemView.findViewById(R.id.email_link);


    }

    public void bindToPost(User user, View.OnClickListener starClickListener) {
        nameView.setText(user.field_full_name);
        emailView.setText(user.name);



    }
}


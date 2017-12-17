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
    public TextView phoneView;
    public ImageView picture;

    public UserViewHolder(View itemView) {
        super(itemView);

        nameView = itemView.findViewById(R.id.field_full_name_view);
        emailView = itemView.findViewById(R.id.email_link);
        phoneView = itemView.findViewById(R.id.phone_link);
        picture = itemView.findViewById(R.id.profile_picture);


    }

    public void bindToPost(User user, View.OnClickListener starClickListener) {
        nameView.setText(user.field_full_name);
        emailView.setText(user.name);
        phoneView.setText(user.field_phone_number);
        Picasso.with(picture.getContext()).load(user.field_profile_picture).into(picture);



    }
}


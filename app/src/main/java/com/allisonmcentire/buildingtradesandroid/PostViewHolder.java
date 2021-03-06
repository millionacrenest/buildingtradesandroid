package com.allisonmcentire.buildingtradesandroid;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by allisonmcentire on 11/26/17.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {


    public TextView titleView;
    public TextView authorView;
    public TextView bodyView;
    public ImageView imageView;
    public TextView pdfLink;
    public TextView pdfName;




    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.post_title);
        authorView = itemView.findViewById(R.id.post_author);
        bodyView = itemView.findViewById(R.id.post_body);
        imageView = (ImageView) itemView.findViewById(R.id.post_photo);
        pdfLink = itemView.findViewById(R.id.post_link);
        pdfName = itemView.findViewById(R.id.post_name);



    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        bodyView.setText(post.body);
        pdfName.setText(post.name);


        Picasso.with(itemView.getContext()).load(post.field_image).into(imageView);
        pdfLink.setText(post.field_document_file);



    }
}

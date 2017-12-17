package com.allisonmcentire.buildingtradesandroid;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by allisonmcentire on 12/17/17.
 */

public class NotesViewHolder extends RecyclerView.ViewHolder {



    //  public TextView pdfLink;
    public TextView noteTitle;
    public TextView noteBody;
    //public ImageView notesImage;




    public NotesViewHolder(View itemView) {
        super(itemView);


        //   pdfLink = itemView.findViewById(R.id.post_link);
        noteTitle = itemView.findViewById(R.id.notes_title);
        noteBody = itemView.findViewById(R.id.notes_body);
        //notesImage = itemView.findViewById(R.id.notes_image);



    }

    public void bindToPost(Notes notes, View.OnClickListener starClickListener) {

        noteTitle.setText(notes.title);
        noteBody.setText(notes.body);
       // Picasso.with(notesImage.getContext()).load(notes.noteImage).into(notesImage);


//        pdfLink.setText(resource.field_document_file);



    }
}


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

public class ResourceViewHolder extends RecyclerView.ViewHolder {



  //  public TextView pdfLink;
    public TextView pdfName;




    public ResourceViewHolder(View itemView) {
        super(itemView);


     //   pdfLink = itemView.findViewById(R.id.post_link);
        pdfName = itemView.findViewById(R.id.resource_name);



    }

    public void bindToPost(Resource resource, View.OnClickListener starClickListener) {

        pdfName.setText(resource.name);


//        pdfLink.setText(resource.field_document_file);



    }
}


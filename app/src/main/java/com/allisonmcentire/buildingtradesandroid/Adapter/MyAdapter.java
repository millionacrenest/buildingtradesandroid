package com.allisonmcentire.buildingtradesandroid.Adapter;

import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allisonmcentire.buildingtradesandroid.News;
import com.allisonmcentire.buildingtradesandroid.R;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.List;
import java.util.regex.MatchResult;

/**
 * Created by allisonmcentire on 11/24/17.
 */

class MyViewHolderWithoutChild extends RecyclerView.ViewHolder {

    public TextView textView;
    public MyViewHolderWithoutChild(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.textView);
    }

}

class MyViewHolderWithChild extends RecyclerView.ViewHolder {

    public TextView textView,textViewChild;
    public RelativeLayout button;
    public ExpandableLinearLayout expandableLayout;

    public MyViewHolderWithChild(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.textView);
        textViewChild = (TextView)itemView.findViewById(R.id.textViewChild);
        button = (RelativeLayout) itemView.findViewById(R.id.button);
        expandableLayout = (ExpandableLinearLayout) itemView.findViewById(R.id.expandableLayout);
    }

}

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<News> items;
    Context context;
    SparseBooleanArray expandState = new SparseBooleanArray();

    public MyAdapter(List<News> items) {
        this.items = items;
        for(int i=0;i<items.size();i++)
            expandState.append(i,false);
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position).isExpandable())
            return 1;
        else
            return 0;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        if(viewType == 0)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.layout_without_child,parent,false);
            return new MyViewHolderWithoutChild(view);
        }
        else
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.layout_with_child,parent,false);
            return new MyViewHolderWithChild(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType())
        {
            case 0:
            {
                MyViewHolderWithoutChild viewHolder = (MyViewHolderWithoutChild)holder;
                News item = items.get(position);
                viewHolder.setIsRecyclable(false);
                viewHolder.textView.setText(item.getTitle());
            }
            break;
            case 1:
            {
                final MyViewHolderWithChild viewHolder = (MyViewHolderWithChild)holder;
                News item = items.get(position);
                viewHolder.setIsRecyclable(false);
                viewHolder.textView.setText(item.getTitle());

                viewHolder.expandableLayout.setInRecyclerView(true);
                viewHolder.expandableLayout.setExpanded(expandState.get(position));
                viewHolder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {


                    @Override
                    public void onPreOpen() {
                        changeRotate(viewHolder.button,0f,180f).start();
                        expandState.put(position,true);
                        

                    }

                    @Override
                    public void onPreClose() {

                        changeRotate(viewHolder.button,180f,0f).start();
                        expandState.put(position,false);
                    }


                });

                viewHolder.button.setRotation(expandState.get(position)?180f:0f);
                viewHolder.button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        viewHolder.expandableLayout.toggle();

                    }
                });
                viewHolder.textViewChild.setText(items.get(position).getBody());
            }
            break;
            default:
                break;
        }
    }

    private ObjectAnimator changeRotate(RelativeLayout button, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(button,"rotation",from,to);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

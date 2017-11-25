package com.allisonmcentire.buildingtradesandroid;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.allisonmcentire.buildingtradesandroid.Adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView list;
    RecyclerView.LayoutManager layoutManager;
    List<News> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_menu);

        list = (RecyclerView)findViewById(R.id.recycler);
        list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

        setData();
    }

    private void setData() {
        for(int i=0;i<20;i++)
        {
            if(i%2==0)
            {
                News item = new News("This is item "+(i+1),true);
                items.add(item);
            }
            else
            {
                News item = new News("This is item "+(i+1),false);
                items.add(item);
            }
        }

        MyAdapter adapter = new MyAdapter(items);
        list.setAdapter(adapter);
    }
}

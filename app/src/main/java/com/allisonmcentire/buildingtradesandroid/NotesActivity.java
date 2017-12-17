package com.allisonmcentire.buildingtradesandroid;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotesActivity extends BaseActivity {

    private Button fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddNotes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(NotesActivity.this, Notes2Activity.class);
                startActivity(intent);
            }
        });
    }
}

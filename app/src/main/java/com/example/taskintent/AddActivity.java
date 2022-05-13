package com.example.taskintent;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class AddActivity extends AppCompatActivity {

    public static final String Title = "com.ace.TaskIntent.title";
    public static final String Description = "com.ace.TaskIntent.description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable color = new ColorDrawable(Color.parseColor("#3EA3EF"));

        actionBar.setBackgroundDrawable(color);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        EditText title = findViewById(R.id.edittitle);
        EditText description = findViewById(R.id.editdesc);
        Button saveBtn = findViewById(R.id.buttonsave);
        title.requestFocus();

        Intent editIntent = getIntent();
        if (editIntent.hasExtra("Update")) {
            setTitle("Update");
            title.setText(editIntent.getStringExtra("Title"));
            description.setText(editIntent.getStringExtra("Description"));

        } else {
            setTitle("Add");
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTitle = title.getText().toString().trim();
                String sDescription = description.getText().toString().trim();
                Intent NewIntent = new Intent();

                if (!sDescription.isEmpty()) {
                    int id = getIntent().getIntExtra("Key", -1);
                    if (id != -1) NewIntent.putExtra("Key", id);
                    NewIntent.putExtra(Title, sTitle);
                    NewIntent.putExtra(Description, sDescription);
                    setResult(RESULT_OK, NewIntent);
                    finish();
                } else {
                    Toast.makeText(AddActivity.this, "Please fill out the input field", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
package com.example.taskintent;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.CaseMap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class AddActivity extends AppCompatActivity {

    public static final String title = "com.example.taskintent.title";
    public static final String description = "com.example.taskintent.description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FDD835"));
        actionBar.setBackgroundDrawable(colorDrawable);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        EditText title = findViewById(R.id.edittitle);
        EditText description = findViewById(R.id.editdesc);
        Button savebtn = findViewById(R.id.buttonsave);
        title.requestFocus();

        Intent editIntent = getIntent();
        if (editIntent.hasExtra("TaskUpdate")) {
            setTitle("Update");
            title.setText(editIntent.getStringExtra("title"));
            description.setText(editIntent.getStringExtra("description"));

        } else {
            setTitle("Create");
        }
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTitle = title.getText().toString().trim();
                String sDescription = description.getText().toString().trim();
                Intent NewIntent = new Intent();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if (!sDescription.isEmpty()) {
                        int id = getIntent().getIntExtra("NoteMeKey", -1);
                        if (id != -1) NewIntent.putExtra("NoteMeKey", id);
                        NewIntent.putExtra(String.valueOf(title), sTitle);
                        NewIntent.putExtra(String.valueOf(description), sDescription);
                        setResult(RESULT_OK, NewIntent);
                        finish();
                    } else {
                        Toast.makeText(AddActivity.this, "Description field cannot be empty!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
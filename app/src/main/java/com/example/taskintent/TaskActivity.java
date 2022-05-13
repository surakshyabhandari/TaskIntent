package com.example.taskintent;


import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private static TaskViewModel viewModel;
    ImageButton addBtn;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView deleteBtn;
    RowActivity adapter;
    static AlertDialog.Builder builder;
    boolean wantEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#3EA3EF"));
        actionBar.setBackgroundDrawable(colorDrawable);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        addBtn = findViewById(R.id.addbtn);
        deleteBtn = findViewById(R.id.deleteicon);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        builder = new AlertDialog.Builder(this);

        adapter = new RowActivity();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        viewModel.getAllNotes().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasksData) {
                adapter.setTaskData(tasksData);
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (!wantEdit) {
                    if (result.getResultCode() == RESULT_OK) {
                        String sTitle = result.getData().getStringExtra(AddActivity.Title);
                        String sDescription = result.getData().getStringExtra(AddActivity.Description);
                        Task insertNote = new Task(sTitle, sDescription);
                        viewModel.insert(insertNote);
                        Toast.makeText(TaskActivity.this, "Task Added", Toast.LENGTH_SHORT).show();
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                    } else {
                        Toast.makeText(TaskActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (result.getResultCode() == RESULT_OK) {
                        int id = result.getData().getIntExtra("Key", -1);
                        if (id == -1) {
                            Toast.makeText(TaskActivity.this, "Something went wrong while updating!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String sTitle = result.getData().getStringExtra(AddActivity.Title);
                        String sDescription = result.getData().getStringExtra(AddActivity.Description);
                        Task Update = new Task(sTitle, sDescription);
                        Update.setId(id);
                        viewModel.update(Update);
                        Toast.makeText(TaskActivity.this, "Note Updated", Toast.LENGTH_SHORT).show();
                        wantEdit = false;
                    }
                }
            }
        });

        adapter.setOnItemClickListener(new RowActivity.OnItemClickListener() {
            @Override
            public void onItemClick(Task note) {
                wantEdit = true;
                Intent intent = new Intent(TaskActivity.this, AddActivity.class);
                intent.putExtra("Key", note.getId());
                intent.putExtra("Title", note.getTitle());
                intent.putExtra("Description", note.getDescription());
                intent.putExtra("Update", "true");
                activityResultLauncher.launch(intent);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(TaskActivity.this, AddActivity.class);
                activityResultLauncher.launch(newIntent);
            }
        });
    }
    public static void deleteNote(Task note) {
        builder.setMessage("Are you sure ?").setTitle("Delete Note")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteNote(note);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
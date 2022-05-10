package com.example.taskintent;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TaskViewModel extends AndroidViewModel{

    private TaskRepository repo;
    private final LiveData<List<Task>> alltasks;

    public TaskViewModel(@NonNull Application application, LiveData<List<Task>> alltaks) {
        super(application);
        this.alltasks = alltaks;
        repo = new TaskRepository(application);
    }

    LiveData<List<Task>> getAllNotes() {

        return alltasks;
    }
    public void insert(Task TaskToBeInserted) {
        repo.insert(TaskToBeInserted);
    }

    public void deleteNote(Task TaskToBeDeleted) {
        repo.deleteNote(TaskToBeDeleted);
    }

    public void update(Task TaskToBeUpdated) {
        repo.updateNote(TaskToBeUpdated);
    }
}

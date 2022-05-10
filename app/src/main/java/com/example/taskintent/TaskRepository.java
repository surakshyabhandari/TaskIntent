package com.example.taskintent;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TaskRepository {

    private TaskDao mytaskdao;
    private LiveData<List<Task>> mytask;

    TaskRepository(Application application) {
        TaskDatabase db = TaskDatabase.getDatabase(application);
        mytaskdao = db.taskDao();
        mytask = mytaskdao.getAllTask();
    }

    LiveData<List<Task>> getAllTask() {
        return mytask;
    }

    void insert(Task TaskToBeInserted) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            mytaskdao.insert(TaskToBeInserted);
        });
    }

    void deleteNote(Task TaskToBeDeleted) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            mytaskdao.delete(TaskToBeDeleted);
        });
    }

    void updateNote(Task TaskToBeUpdated) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            mytaskdao.update(TaskToBeUpdated);
        });
    }
}

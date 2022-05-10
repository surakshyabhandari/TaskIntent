package com.example.taskintent;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task TaskToBeInserted);

    @Delete
    void delete(Task TaskToBeDeleted);

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTask();

    @Update
    void update(Task TaskToBeUpdated);
}

package com.example.ap1_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    TaskDAO taskDAO;
    ItemsAdapter testAdapter;
    RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        rvList = findViewById(R.id.list);

        initDb();

        Task p1 = new Task("Josué", "Eliel");


        List<Task> tasks = taskDAO.getAll();

        for (Task p: tasks) {
            Log.d("sid-tag", p.toString());
        }

        testAdapter = new ItemsAdapter(tasks);

        rvList.setAdapter(testAdapter);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                addItemHandler(p1);
                Intent intent = new Intent(MainActivity.this, Form.class);
                startActivity(intent);
            }
        });

        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new TouchHandler(0, ItemTouchHelper.LEFT)
        );

        touchHelper.attachToRecyclerView(rvList);



    }


    void initDb(){
        this.db = Room.databaseBuilder(
                        this,
                        AppDatabase.class,
                        "db-task"
                ).enableMultiInstanceInvalidation()
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        this.taskDAO = db.taskDao();
    }


    private void addItem(ItemsAdapter adapter, Task item){
        adapter.getTasks().add(0, item);
    }

    private void addItemHandler(Task item) {
        addItem(testAdapter, item);
        taskDAO.insertAll(item);
        testAdapter.notifyItemInserted(0);
    }

    private class TouchHandler extends ItemTouchHelper.SimpleCallback {

        public TouchHandler(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();

            Collections.swap(testAdapter.getTasks(), from, to);
            testAdapter.notifyItemMoved(from, to);


            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int index = viewHolder.getLayoutPosition() - 1;
            if(index > 0) {
                testAdapter.getTasks().remove(viewHolder.getAdapterPosition());
                testAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                taskDAO.delete(testAdapter.getTasks().get(index));
            }


        }
    }

}
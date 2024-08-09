package com.example.ap1_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

public class Form extends AppCompatActivity {

    AppDatabase db;
    TaskDAO taskDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText title = findViewById(R.id.title);
        EditText desc = findViewById(R.id.desc);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                handleSubmit(title.getText().toString(), desc.getText().toString());
            }
        });


    }

    public void handleSubmit (String title, String desc){
        Intent intent = new Intent(Form.this, MainActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("desc", desc);

        Task item = new Task(title, desc);

        taskDAO.insertAll(item);

        startActivity(intent);
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
}
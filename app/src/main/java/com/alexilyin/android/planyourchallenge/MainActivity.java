package com.alexilyin.android.planyourchallenge;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.alexilyin.android.planyourchallenge.model.Task;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    RecyclerView recyclerView;
    private static final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = DBHelper.getInstance(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        Button btnAddTask = (Button) findViewById(R.id.add_task);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(DetailsActivity.getIntent(context, -1));
            }
        });

//        Task tempTask = new Task();
//
//        tempTask.title = "Title " + random.nextInt(1000);
//        tempTask.isDone = Boolean.TRUE;
//        dbHelper.insertTask(tempTask);
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView.setAdapter(new MyAdapter(this, dbHelper.queryTaskListCursor()));
    }
}

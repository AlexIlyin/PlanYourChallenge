package com.alexilyin.android.planyourchallenge;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alexilyin.android.planyourchallenge.m.DBHelper;
import com.alexilyin.android.planyourchallenge.m.model.Task;

/**
 * Created by user on 27.03.16.
 */
public class DetailsActivity extends AppCompatActivity {

    private static final String INTENT_KEY_ID = "id";
    private static final long EMPTY_VALUE_ID = -1;

    private TextView idView;
    private EditText titleView;
    private CheckBox isDoneView;
    private Button delButton;

    DBHelper helper;
    long id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task_activity);

        idView = (TextView) findViewById(R.id.edit_task_id);
        titleView = (EditText) findViewById(R.id.edit_task_title);
        isDoneView = (CheckBox) findViewById(R.id.edit_task_is_done);
        delButton = (Button) findViewById(R.id.edit_task_delete_button);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != -1) {
                    Task task = new Task();
                    task._id = id;
                    helper.deleteTask(task);
                    DetailsActivity.this.finish();
                }
            }
        });

        helper = DBHelper.getInstance(this);

        Intent intent = getIntent();
        id = intent.getLongExtra(INTENT_KEY_ID, EMPTY_VALUE_ID);
    }

    public static Intent getIntent(Context context, long id) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(INTENT_KEY_ID, id);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (id != -1) {
            Task task = helper.queryTask(id);
            idView.setText(String.valueOf(task._id));
            titleView.setText(task.title);
            isDoneView.setChecked(task.isDone);
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle("Save changes?")
                .setMessage("Save changes?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Task tempTask = new Task();
                        tempTask.title = titleView.getText().toString();
                        tempTask.isDone = isDoneView.isChecked();

                        if (id == -1) { // add new Task
                            helper.insertTask(tempTask);
                        } else { // update exists Task
                            tempTask._id = id;
                            helper.updateTask(tempTask);
                        }
                        finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }
}

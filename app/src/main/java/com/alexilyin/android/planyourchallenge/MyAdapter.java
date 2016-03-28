package com.alexilyin.android.planyourchallenge;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alexilyin.android.planyourchallenge.model.Task;

/**
 * Created by user on 27.03.16.
 */
class MyAdapter extends CursorRecyclerViewAdapter<MyAdapter.Holder> {

    Cursor cursor;
    LayoutInflater layoutInflater;

    public static class Holder extends RecyclerView.ViewHolder {

        long id;
        TextView title;
        CheckBox isDone;


        public Holder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.item_layout_title);
            isDone = (CheckBox) itemView.findViewById(R.id.item_layout_checkbox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    context.startActivity(DetailsActivity.getIntent(context, id));
                }
            });
        }
    }

    public MyAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.cursor = cursor;
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, Cursor cursor) {

        Task task = DBContract.TaskTable.getObject(cursor);
        viewHolder.id = task._id;
        viewHolder.title.setText(task.title);
        viewHolder.isDone.setChecked(task.isDone);

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) parent
                    .getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        View view = layoutInflater.inflate(R.layout.item_layout, parent, false);

        return new Holder(view);
    }


    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}

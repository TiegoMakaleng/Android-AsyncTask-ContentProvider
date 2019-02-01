package com.app.todos.provider;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.contentproviderdemo.R;

public class TaskAdapter extends ArrayAdapter<Task> {
   
	private Cursor mCursor;
	private Context mContext;
	
	public TaskAdapter(Context context, int resource, Cursor cursor) {
		super(context, resource);
		mContext = context;
		mCursor = cursor;
	}
	
	@Override
	public View getView(int position, View itemView, ViewGroup parent) {
		
		TaskViewHolder holder = null;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (itemView == null) {
			itemView = inflater.inflate(R.layout.task_layout, parent, false);
			holder = new TaskViewHolder(itemView);
		} else {
			holder = (TaskViewHolder) itemView.getTag();
		}
		
		 // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(TaskContract.TaskEntry._ID);
        int descriptionIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION);
        int priorityIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String description = mCursor.getString(descriptionIndex);
        int priority = mCursor.getInt(priorityIndex);
		
        //Set values
        holder.taskDescriptionView.setText(description);

        // Programmatically set the text and color for the priority TextView
        holder.priorityView.setText(String.valueOf(priority));

		return itemView;
	}
	
	public class TaskViewHolder {
		
		public final TextView taskDescriptionView;
		public final TextView priorityView;
		
		public TaskViewHolder(View itemView) {
			
			taskDescriptionView = (TextView) itemView.findViewById(R.id.taskDescription);
			priorityView = (TextView) itemView.findViewById(R.id.priorityTextView);
			
			itemView.setTag(this);
		}
	}
	
	@Override
	public int getCount() {
		return mCursor == null ? 0 : mCursor.getCount();
	}

}

package com.app.contentproviderdemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.app.todos.provider.TaskAdapter;
import com.app.todos.provider.TaskContract.TaskEntry;


public class MainActivity extends Activity {

    // Member variables for the adapter and ListView
    private ListView mRecyclerView;

    private Cursor mCursorTasks;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Set the ListView to its corresponding view
        mRecyclerView = (ListView) findViewById(R.id.recyclerViewTasks);
        
        /*
         Set the Button to its corresponding View.
         Attach an OnClickListener to it, so that when it's clicked, a new intent will be created
         to launch the AddTaskActivity.
         */
        Button fabButton = (Button) findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(addTaskIntent);
            }
        });
        
        new TasksAsyncTask().execute();

    }
    
    @Override
    protected void onResume() {
        super.onResume();
        new TasksAsyncTask().execute();
    }

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	mCursorTasks.close();
    }
    
    public class TasksAsyncTask extends AsyncTask<Void, Void, Cursor> {
    	
		@Override
		protected Cursor doInBackground(Void... params) {
			ContentResolver resolver = getContentResolver();
			Cursor cursor = resolver.query(TaskEntry.CONTENT_URI, null, null, null, null);
			return cursor;
		}
		
		@Override
		protected void onPostExecute(Cursor cursorTasks) {
			super.onPostExecute(cursorTasks);
			// set up data to display on MainActivity
			mCursorTasks = cursorTasks;

			TaskAdapter adapter = new TaskAdapter(MainActivity.this, R.layout.task_layout, mCursorTasks);
            mRecyclerView.setAdapter(adapter);
			// refresh the list view
			adapter.notifyDataSetChanged();
		}
    }

}


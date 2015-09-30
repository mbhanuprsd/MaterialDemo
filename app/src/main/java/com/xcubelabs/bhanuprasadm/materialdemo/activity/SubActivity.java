package com.xcubelabs.bhanuprasadm.materialdemo.activity;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.xcubelabs.bhanuprasadm.materialdemo.R;

public class SubActivity extends AppCompatActivity {

    private static final String TAG = "SubActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "dispatchTouchEvent : DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "dispatchTouchEvent : UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "dispatchTouchEvent : MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "dispatchTouchEvent : CANCEL");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG, "dispatchTouchEvent : POINTER UP "+event.getPointerCount());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "dispatchTouchEvent : POINTER DOWN "+event.getPointerCount());
                break;
            default:
                break;
        }
        boolean b = super.dispatchTouchEvent(event);
        Log.i(TAG, "dispatchTouchEvent RETURNS " + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "touchEvent : DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "touchEvent : UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "touchEvent : MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "touchEvent : CANCEL");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG, "touchEvent : POINTER UP "+event.getPointerCount());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "touchEvent : POINTER DOWN "+event.getPointerCount());
                break;
            default:
                break;
        }
        boolean b = super.onTouchEvent(event);
        Log.i(TAG, "touchEvent RETURNS "+b);
        return b;
    }
}

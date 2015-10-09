package com.playphone.sdk3sample.helper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.playphone.psgn.PSGN;

/**
 * Created by dgk on 9/23/15.
 */
public class ChildActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {
        super.onResume();
        PSGN.incrementActivity(this);
    }

    protected void onPause() {
        super.onPause();
        PSGN.decrementActivity(this);
    }

}

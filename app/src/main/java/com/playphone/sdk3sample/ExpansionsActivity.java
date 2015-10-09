package com.playphone.sdk3sample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.playphone.psgn.expansion.ExpansionReceiver;
import com.playphone.psgn.expansion.ExpansionUtils;
import com.playphone.psgn.expansion.PSGNExpansion;
import com.playphone.sdk3sample.helper.ChildActivity;
import com.playphone.sdk3sample.helper.SDKDemoConstants;

import java.util.ArrayList;

public class ExpansionsActivity extends ChildActivity {

    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expansions);

        // set the breadcrumbs text
        TextView txtBreadCrumbs = (TextView) findViewById(R.id.txtBreadCrumbs);
        txtBreadCrumbs.setText("Home > Expansions");
        shared_preferences = getSharedPreferences("shared_preferences_test",
                MODE_PRIVATE);
        boolean firstTime = shared_preferences.getBoolean("First Time", true);

        if (firstTime) {
            deleteEVERYTHING();
            shared_preferences_editor = shared_preferences.edit();
            shared_preferences_editor.putBoolean("First Time", false);
            shared_preferences_editor.commit();
        } else {
            loadLocalExpansions();
        }

        Button btnExpansionList = (Button) findViewById(R.id.btnExpansionList);
        btnExpansionList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ExpansionUtils.getServerExpansions(getApplicationContext(),
                        "com.playphone.expansion_example3", false,
                        new ExpansionReceiver() {
                            @Override
                            public void receiveExpansions(
                                    final PSGNExpansion[] expansions) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateData(expansions);
                                        loadLocalExpansions();
                                    }
                                });
                            }

                            @Override
                            public void cantReceiveExpansions() {
                                Log.d(SDKDemoConstants.TAG, "Expansions could not be received successfully");
                            }

                            @Override
                            public void onProgress(long alreadyDownloaded, long leftToDownload) {
                                Log.d(SDKDemoConstants.TAG, "Expansions progress " + alreadyDownloaded + " left " + leftToDownload);
                            }
                        });
            }

        });
    }

    private void deleteEVERYTHING() {
        PSGNExpansion[] Lexp = ExpansionUtils.getLocalExpansions(
                getApplicationContext(), "com.playphone.expansion_example3",
                false);
        if (Lexp != null) {
            for (PSGNExpansion expansion : Lexp)
                ExpansionUtils.deleteExpansion(expansion);
        }

    }

    private void loadLocalExpansions() {
        ArrayList<String> locallist = new ArrayList<String>();
        PSGNExpansion[] Lexp = ExpansionUtils.getLocalExpansions(
                getApplicationContext(), "com.playphone.expansion_example3",
                false);
        if (Lexp != null && Lexp.length != 0) {
            for (PSGNExpansion expansion : Lexp) {
                locallist.add(expansion.getName() + " + " + expansion.getType()
                        + " + " + expansion.getVersion() + " + "
                        + expansion.getDescription());
                Log.d("expansion data", expansion.toString());
            }
        } else {
            locallist.add("No Local Expansions");
        }

        final ListView lelv = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> localarrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.expansion_item, android.R.id.text1,
                locallist);
        lelv.setClickable(true);
        lelv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Log.d("onclick Stuff", lelv.getItemAtPosition(arg2).toString());
                final String[] LocalValues = lelv.getItemAtPosition(arg2)
                        .toString().split("\\+ ");
                final AlertDialog alertDialog = new AlertDialog.Builder(
                        ExpansionsActivity.this).create();

                // Setting Dialog Title
                alertDialog.setTitle("Selected Expansion");

                // Setting Dialog Message
                alertDialog.setMessage("Application:\n" + LocalValues[1]
                        + "\nExpansion Name:\n" + LocalValues[0]
                        + "\nVersion:\n" + LocalValues[2] + "\nDescription:\n"
                        + LocalValues[3]);

                // Setting OK Button
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                alertDialog.dismiss();
                            }
                        });

                // Showing Alert Message
                alertDialog.show();
            }
        });
        lelv.setAdapter(localarrayAdapter);
    }

    private void updateData(PSGNExpansion[] data) {
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < data.length; ++i) {
            list.add(data[i].getName() + " + " + data[i].getType());
            Log.d("expansion data", data[i].toString());
        }
        final ListView lv = (ListView) findViewById(R.id.listView2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.expansion_item, android.R.id.text1,
                list);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Log.d("onclick Stuff", lv.getItemAtPosition(arg2).toString());
                String[] values = lv.getItemAtPosition(arg2).toString()
                        .split("\\+ ");
                Log.d("expansion stuff", values[0]);
                Log.d("expansion stuff", values[1]);
                Toast.makeText(getApplicationContext(),
                        "Now Downloading Selection...", Toast.LENGTH_SHORT)
                        .show();
                PSGNExpansion Sexp = ExpansionUtils.getExpansion(
                        getApplicationContext(), values[1].trim(),
                        values[0].trim(), true);
                Log.d(SDKDemoConstants.TAG,
                        "" + values[1].trim() + values[0].trim() + true);
                if (Sexp != null) {
                    Log.d(SDKDemoConstants.TAG,
                            "we got an expansion from the Server!");
                    Log.d(SDKDemoConstants.TAG, "name is: " + Sexp.getName());
                    Toast.makeText(getApplicationContext(),
                            "Download Complete", Toast.LENGTH_SHORT).show();
                    loadLocalExpansions();
                } else {
                    Log.d(SDKDemoConstants.TAG, "No expansion :(");
                    Toast.makeText(getApplicationContext(),
                            "No Expansion Was Found :,(", Toast.LENGTH_SHORT)
                            .show();
                    loadLocalExpansions();
                }

            }
        });
        lv.setAdapter(arrayAdapter);
    }
}
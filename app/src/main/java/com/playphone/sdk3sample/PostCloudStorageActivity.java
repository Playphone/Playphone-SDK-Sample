package com.playphone.sdk3sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.playphone.psgn.PSGN;
import com.playphone.psgn.PSGNConst;
import com.playphone.psgn.PSGNHandler;
import com.playphone.sdk3sample.helper.ChildActivity;
import com.playphone.sdk3sample.helper.SDKDemoConstants;
import com.playphone.sdk3sample.helper.SimplePlayerData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class PostCloudStorageActivity extends ChildActivity implements OnClickListener, Handler.Callback {
    EditText editInput;
    TextView txtResult;
    EditText editPlayerName;
    EditText editPlayerLevel;
    EditText editPlayerLife;
    SimplePlayerData player;


    Random rand = new Random();
    Handler handler = new Handler(this);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_cloudstorage);

        // set the breadcrumbs text
        TextView txtBreadCrumbs = (TextView) findViewById(R.id.txtBreadCrumbs);
        txtBreadCrumbs.setText("Home > Data API");


        Button btnUpload = (Button) findViewById(R.id.btnWrite);
        btnUpload.setOnClickListener(this);

        txtResult = (TextView) findViewById(R.id.txtResult);


        player = new SimplePlayerData();
        editPlayerName = (EditText) findViewById(R.id.txt_player_name);
        editPlayerName.setText(player.getName());

        editPlayerLevel = (EditText) findViewById(R.id.txt_level);
        editPlayerLevel.setText(player.getLevel());

        editPlayerLife = (EditText) findViewById(R.id.txt_life);
        editPlayerLife.setText(player.getLife());

        Button btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean remoteLoading = false;
                loadData(remoteLoading);

            }
        });


        Button btnReadRemoteCloudStorage = (Button) findViewById(R.id.read_remote_cloud_storage);
        btnReadRemoteCloudStorage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean remoteLoading = true;
                loadData(remoteLoading);
            }
        });

        Button btnClearAll = (Button) findViewById(R.id.btn_clear_all);
        btnClearAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editPlayerName.setText("");
                editPlayerLevel.setText("");
                editPlayerLife.setText("");
            }
        });

    }


    @Override
    public void onClick(View arg0) {
        // write to cloud
        player.setName(editPlayerName.getText().toString());
        player.setLevel(editPlayerLevel.getText().toString());
        player.setLife(editPlayerLife.getText().toString());
        JSONObject playerDataJSON = new JSONObject(player.getMapFromObject());
        Log.i(SDKDemoConstants.TAG, "Saving JSON: " + playerDataJSON.toString());
        PSGN.setGamePlayerData(playerDataJSON);
        Log.i(SDKDemoConstants.TAG, "Saving done.");

        Toast.makeText(this, "Data successfully written", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean handleMessage(Message msg) {
        if ("addToResult".equalsIgnoreCase(msg.getData().getString("action"))) {
            String dataToAdd = msg.getData().getString("append");
            txtResult.setText(txtResult.getText() + dataToAdd);
            txtResult.invalidate();
            Log.d("playphone", "HandleMessage: " + txtResult.getText().toString());
        }
        return true;
    }


    private void loadData(boolean remoteLoading) {
        JSONObject gameData = null;
        if (remoteLoading) {
            final boolean currentRemoteLoading = remoteLoading;
            PSGN.getRemoteGamePlayerData(new PSGNHandler() {
                @Override
                public void onComplete(HashMap<String, Object> data) {
                    final HashMap dataLocal = data;

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            parseGameData((JSONObject) dataLocal.get(PSGNConst.HASH_VALUES_PLAYER_DATA),
                                    currentRemoteLoading);
                        }
                    });

                }

                @Override
                public void onFail(HashMap<String, Object> arg0) {
                    Log.w(SDKDemoConstants.TAG, "Loading remote game data failed");

                }
            });
        } else {
            gameData = PSGN.getGamePlayerData();
            parseGameData(gameData, remoteLoading);
        }

    }

    private void parseGameData(JSONObject gameData, boolean remoteLoading) {
        Log.i(SDKDemoConstants.TAG, "Read game data: " + gameData.toString());
        if (gameData.length() == 0) {
            Log.d(SDKDemoConstants.TAG, "No data has been saved yet");
            Toast.makeText(PostCloudStorageActivity.this, "No data has been saved yet", Toast.LENGTH_SHORT).show();
            return;
        }

        //refresh the data
        try {
            player.updateFromJson(gameData);
        } catch (JSONException e) {
            Log.e(SDKDemoConstants.TAG, "Could not tranlate from JSON to object: " + gameData.toString());
        }

        //update textfields based on object
        updateTextFields();

        //display success message
        String successMessage = "";
        if (remoteLoading)
            successMessage = "Remote data has been successfully loaded.";
        else
            successMessage = "Local data has been successfully loaded.";

        Toast.makeText(PostCloudStorageActivity.this, successMessage, Toast.LENGTH_SHORT).show();
    }


    private void updateTextFields() {
        editPlayerName.setText(player.getName());
        editPlayerLevel.setText(player.getLevel());
        editPlayerLife.setText(player.getLife());
        return;
    }


}

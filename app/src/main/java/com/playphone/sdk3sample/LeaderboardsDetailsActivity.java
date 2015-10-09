package com.playphone.sdk3sample;

import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LeaderboardsDetailsActivity extends ChildActivity {
    private EditText editInput;
    private Button btnLeaderboard;
    private TextView txtScore;
    private Button btnScore;
    private EditText editID;

    private JSONObject currentScores;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_details);

        // set the breadcrumbs text
        TextView txtBreadCrumbs = (TextView) findViewById(R.id.txtBreadCrumbs);
        txtBreadCrumbs.setText("Home > Leaderboards");

        editID = (EditText) findViewById(R.id.txtLeaderboardName);
        editInput = (EditText) findViewById(R.id.editInput);
        txtScore = (TextView) findViewById(R.id.txtDisplayScore);

        btnLeaderboard = (Button) findViewById(R.id.btnUpdateScore);
        btnLeaderboard.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int score;
                int leaderboard_id;
                try {
                    score = Integer.valueOf(editInput.getText().toString());
                    leaderboard_id = Integer.valueOf(editID.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(LeaderboardsDetailsActivity.this, "You need to type an integer",
                            Toast.LENGTH_SHORT).show();
                    return;
                } catch (Exception e) {
                    Log.e(SDKDemoConstants.TAG,
                            "Unexpected error while processing leaderboard ID from user input: "
                                    + e.getMessage(), e);
                    return;
                }

                editInput.clearFocus();
                editID.clearFocus();

                PSGN.Leaderboards.submitScore(String.valueOf(leaderboard_id), score);

                Toast.makeText(LeaderboardsDetailsActivity.this,
                        "Updated your score of "
                                + editInput.getText().toString()
                                + " on the Leaderboards", Toast.LENGTH_LONG)
                        .show();

            }
        });

        btnScore = (Button) findViewById(com.playphone.sdk3sample.R.id.btnGetScore);
        btnScore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int leaderboard_id;
                try {
                    leaderboard_id = Integer.valueOf(editID.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(LeaderboardsDetailsActivity.this, "You need to type an integer for leaderboard ID",
                            Toast.LENGTH_SHORT).show();
                    return;
                } catch (Exception e) {
                    Log.e(SDKDemoConstants.TAG,
                            "Unexpected error while processing leaderboard ID from user input: "
                                    + e.getMessage(), e);
                    return;
                }

                editInput.clearFocus();
                editID.clearFocus();
                txtScore.setText("");

                grabLeaderboardScores(String.valueOf(leaderboard_id));
            }
        });
    }

    public void grabLeaderboardScores(String leaderboard_id) {
        PSGN.Leaderboards.getLeaderboard(leaderboard_id, new PSGNHandler() {
            @Override
            public void onComplete(HashMap<String, Object> hashMap) {
                Object val = hashMap.get(PSGNConst.HASH_VALUES_LEADERBOARDS);
                if (val != null && val instanceof JSONObject) {
                    currentScores = (JSONObject) val;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentScores != null) {
                            txtScore.setText(currentScores.toString());
                        } else {
                            txtScore.setText("No score available. Please try again.");
                        }
                    }
                });
            }

            @Override
            public void onFail(HashMap<String, Object> hashMap) {
                Log.e(SDKDemoConstants.TAG, "Fail to get leaderboard scores.");
            }
        });
    }
}

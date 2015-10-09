package com.playphone.sdk3sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.playphone.psgn.PSGN;
import com.playphone.psgn.PSGNConst;
import com.playphone.psgn.PSGNHandler;
import com.playphone.sdk3sample.helper.ChildActivity;
import com.playphone.sdk3sample.helper.SDKDemoConstants;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;

public class CurrentUserInfoActivity extends ChildActivity {

    private ImageView avatarImage;
    private TableLayout infoTable;

    private class FillUserAvatarTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... paramArrayOfParams) {

            Bitmap result = null;

            for (String url : paramArrayOfParams) {

                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    result = BitmapFactory.decodeStream(content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                avatarImage.setImageBitmap(result);
            }
        }
    }

    FillUserAvatarTask task = new FillUserAvatarTask();

    private void addRowToInfoTable(String text, String description) {
        TableRow tr = new TableRow(this);
        TextView cell;

        cell = new TextView(this);
        cell.setText(text);
        tr.addView(cell);

        cell = new TextView(this);
        cell.setText(description);
        tr.addView(cell);

        infoTable.addView(tr);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_user_info);

        // set the breadcrumbs text
        TextView txtBreadCrumbs = (TextView) findViewById(R.id.txtBreadCrumbs);
        txtBreadCrumbs.setText("Home > Current User Info");

        final TextView infoTitle = (TextView) findViewById(R.id.infoTitle);

        // get data for the current user
        Log.i(SDKDemoConstants.TAG, "calling user data info");
        PSGN.getData(PSGNConst.PSGN_PLAYER, new PSGNHandler() {
            @Override
            public void onComplete(HashMap<String, Object> data) {
                String guid;
                try {
                    JSONObject playerData = (JSONObject) data.get(PSGNConst.HASH_VALUES_PLAYER_DATA);
                    guid = playerData.getString("guid");
                    Log.i(SDKDemoConstants.TAG, "Guid of current user: " + guid);
                } catch (JSONException e) {
                    Log.e(SDKDemoConstants.TAG, "Error while getting guid of user.", e);
                }
                infoTitle.setText("\nPlayer data: " + data.get(PSGNConst.HASH_VALUES_PLAYER_DATA).toString());
                Log.i(SDKDemoConstants.TAG, data.toString());
            }

            @Override
            public void onFail(HashMap<String, Object> arg0) {
                Log.w(SDKDemoConstants.TAG, "Getting PSGN player data failed");

            }
        });

        PSGN.getData(PSGNConst.PSGN_FRIENDS, new PSGNHandler() {
            @Override
            public void onComplete(HashMap<String, Object> data) {
                JSONObject friendsData = (JSONObject) data.get(PSGNConst.HASH_VALUES_FRIEND_DATA);
                infoTitle.setText(infoTitle.getText().toString() + " \n\nFriends: " + friendsData.toString());
            }

            @Override
            public void onFail(HashMap<String, Object> arg0) {
                Log.w(SDKDemoConstants.TAG, "PSGN get friends failed");
            }
        });
        JSONObject player_data = PSGN.getGamePlayerData();
        infoTitle.setText(infoTitle.getText().toString() + " \n\nSaved Data:" + player_data.toString());


    }

}

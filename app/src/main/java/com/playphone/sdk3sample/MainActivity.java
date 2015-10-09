package com.playphone.sdk3sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.playphone.psgn.PSGN;
import com.playphone.psgn.PSGNConst;
import com.playphone.psgn.PSGNHandler;
import com.playphone.sdk3sample.helper.SDKDemoConstants;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // application specific information
    final private String TAB = "       ";
    private final String TOAST_INFO = "Select one of the items in the list to see more details";

    private Handler mHandler = new Handler();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(SDKDemoConstants.TAG, "onCreate has been called for MainActivity");
        super.onCreate(savedInstanceState);

        final Entry[] entries = getEntries();

        setContentView(R.layout.main_listview);

        ListView lv = (ListView) findViewById(R.id.main_list);
        lv.setAdapter(new ArrayAdapter<Entry>(this, R.layout.main_menu_item, entries));

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                entries[position].run();
            }
        });

       
        /*INITIALIZATION PROPERTIES**/

        //Sets Icon Location
        PSGN.setProperty(PSGNConst.PROPERTY_ICON_GRAVITY, String.valueOf(Gravity.TOP | Gravity.RIGHT), this);

        //Sets developers Secret Key. This is unique for all developers and can be found here https://developer.playphone.com/company/info
        String secretKey = "REPLACE_WITH_YOUR_SECRET_KEY";

        if (secretKey.contains("REPLACE")) {
            throw new RuntimeException("Please put your secret key in MainActivity.java.");
        }

        PSGN.setProperty(PSGNConst.PROPERTY_SECRET_KEY, secretKey, this);

        //Allows for marketing campaigns start actions in your game. 
        PSGN.setLaunchIntent(getIntent(), this);
        /*INITIALIZATION PROPERTIES**/
                
        
        /*INITIALIZATION OF THE PLAYPHONE SDK**/
        PSGN.init(this, new PSGNHandler() {

            @Override
            public void onComplete(HashMap<String, Object> values) {
                /* The icon must be shown to go live in the Playphone network */
                Log.d(SDKDemoConstants.TAG, "PSGN init completed, showing the icon!");
                PSGN.doAction(PSGNConst.PSGN_SHOW_ICON);
                PSGN.doQueuedLaunchAction();
            }

            @Override
            public void onFail(HashMap<String, Object> arg0) {
                Log.d(SDKDemoConstants.TAG, "Playphone SDK init failed, continuing in offline mode");
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(
                                MainActivity.this,
                                "Playphone SDK init failed, continuing in offline mode",
                                /* If the init fails it is important not to use any PSGN features */
                                Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        /*INITIALIZATION OF THE PLAYPHONE SDK**/
    }

    /* SDK LIFECYCLE */

    @Override
    protected void onResume() {
        super.onResume();
        PSGN.incrementActivity(this);
        PSGN.doLaunchAction(false, this);
        jumpToLaunchScreen();
    }

    protected void onPause() {
        super.onPause();
        PSGN.decrementActivity(this);
    }

    protected void jumpToLaunchScreen() {
        String screen = PSGN.getAndClearLaunchScreen();
        if (PSGNConst.PSGN_LAUNCH_SCREEN_OFFERS.equals(screen)) {
            startActivity(new Intent(MainActivity.this, BillingActivity.class));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PSGN.setLaunchIntent(intent, this);
    }

    /* LIST ITEMS */

    protected interface Entry {
        String toString();

        void run();
    }

    protected Entry[] getEntries() {
        return new Entry[]{new Entry() {
            @Override
            public String toString() {
                return "SDK Features:";
            }

            @Override
            public void run() {
                Toast.makeText(MainActivity.this, TOAST_INFO,
                        Toast.LENGTH_SHORT).show();
            }
        }, new Entry() {
            @Override
            public String toString() {
                return TAB + "Playphone Billing";
            }

            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,
                        BillingActivity.class));
            }
        }, new Entry() {
            @Override
            public String toString() {
                return TAB + "Google Play Billing";
            }

            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,
                        BillingActivity.class).putExtra("USE_GOOGLE_PLAY", true));
            }
        }, new Entry() {
            @Override
            public String toString() {
                return TAB + "Data Storage";
            }

            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,
                        PostCloudStorageActivity.class));
            }
        }, new Entry() {
            @Override
            public String toString() {
                return TAB + "Leaderboards";
            }

            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,
                        LeaderboardsDetailsActivity.class));
            }
        }, new Entry() {
            @Override
            public String toString() {
                return TAB + "Achievements";
            }

            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,
                        PostAchievementActivity.class));
            }
        }, new Entry() {
            @Override
            public String toString() {
                return TAB + "Current User Info";
            }

            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,
                        CurrentUserInfoActivity.class));
            }
        }, new Entry() {
            @Override
            public String toString() {
                return TAB + "Expansions";
            }

            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,
                        ExpansionsActivity.class));
            }
        }, new Entry() {
            @Override
            public String toString() {
                return TAB + "Application Info";
            }

            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,
                        ApplicationInfoActivity.class));
            }

        }};
    }

}

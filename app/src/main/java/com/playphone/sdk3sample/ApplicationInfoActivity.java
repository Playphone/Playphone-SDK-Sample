package com.playphone.sdk3sample;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.playphone.psgn.PSGN;
import com.playphone.psgn.PSGNConst;
import com.playphone.sdk3sample.helper.ChildActivity;

public class ApplicationInfoActivity extends ChildActivity {

    TextView appInfoText;
    Button moreGames;
    Button openStorePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_info);

        TextView txtBreadCrumbs = (TextView) findViewById(R.id.txtBreadCrumbs);
        txtBreadCrumbs.setText("Home > Application Info");

        appInfoText = (TextView) findViewById(R.id.appinfotext);
        moreGames = (Button) findViewById(R.id.more_games);
        openStorePage = (Button) findViewById(R.id.open_store_page);

        PackageManager pm = getPackageManager();
        StringBuffer resultInfo = new StringBuffer();

        resultInfo.append("Application  name : ").append(pm.getApplicationLabel(getApplicationInfo())).append("\n");

        try {
            PackageInfo packInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            resultInfo.append("Application  version: ").append(packInfo.versionName).append("\n");

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        resultInfo.append("Android library version :  ").append(getApplicationInfo().targetSdkVersion).append("\n");

        appInfoText.setText(resultInfo.toString());

        moreGames.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PSGN.doAction(PSGNConst.PSGN_MORE_GAMES);
            }
        });

        openStorePage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PSGN.doAction(PSGNConst.PSGN_OPEN_STORE_PAGE);
            }
        });
    }

}

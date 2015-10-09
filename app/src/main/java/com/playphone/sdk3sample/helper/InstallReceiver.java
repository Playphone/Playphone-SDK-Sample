package com.playphone.sdk3sample.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
 * Only used for internal testing of InstallReceiver, do not use.
 */
public class InstallReceiver extends BroadcastReceiver {

	private String TAG = "INSTALLRECEIVER";
	
	/*
	 * add this to manifest to test
	 * <receiver
     *       android:name="com.playphone.sdk2sample.helper.InstallReceiver"
     *       android:exported="true" >
     *       <intent-filter>
     *           <action android:name="com.android.vending.INSTALL_REFERRER" />
     *       </intent-filter>
     *   </receiver>
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		        // Return if this is not the right intent.
        if (! intent.getAction().equals("com.android.vending.INSTALL_REFERRER")) { //$NON-NLS-1$
        	Log.d(TAG, "This is not the right intent");
        	return;
        }
 
        String referrer = intent.getStringExtra("referrer"); 
        Log.d(TAG, "Got this referrer: " + referrer);
        
        // this was used to test passing on the intent to all other receivers, it should remain commented out
        /*
        intent.setComponent(null);
        intent.setPackage(context.getPackageName());
        List<ResolveInfo> l=context.getPackageManager().queryBroadcastReceivers(intent, 0);
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			ResolveInfo resolveInfo = (ResolveInfo) iterator.next();
			
			Log.d(TAG, "Found " + resolveInfo.activityInfo.name);
			
			if(!resolveInfo.activityInfo.name.contains("playphone.sdk2sample")){
				intent.setComponent(new ComponentName(context, resolveInfo.activityInfo.name));
				context.sendBroadcast(intent);
			}
		}*/
		
	}
}

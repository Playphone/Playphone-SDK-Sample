Playphone-SDK-Sample
==================

_This is a sample application demonstrating usage of Playphone SDK. **You will not be able to run this sample as-is**. Please follow the instructions as described below before you run the sample._

Instructions on how to run the sample
-------------------

Prerequisites: You must have a Playphone Developer account. If you do not have an account, you can create a new one [here](http://developer.playphone.com).

1. [Clone the repository](#step-1-clone-the-repository)
2. [Create a new game on Developer Portal](#step-2-create-a-new-game-on-developer-portal)
3. [Open Project in Android Studio](#step-3-open-project-in-android-studio)
4. [Replace Secret Key and License Key](#step-4-replace-secret-key-and-license-key)
5. [Change package name](#step-5-change-package-name)
6. [Add In-app Products](#step-6-add-in-app-products)
7. [Add Leaderboards](#step-7-add-leaderboards)
8. [Add Achievements](#step-8-add-achievements)

--------------------

Step 1: Clone the repository
--------------------
  - `git clone git@github.com:Playphone/Playphone-SDK-Sample.git`

Step 2: Create a new game on Developer Portal
--------------------
  - Login to [Playphone Developer Portal](http://developer.playphone.com) with your Playphone Developer account
  - On the left navigation bar, click on 'My Games' tab
  - Click on 'Add Game'
  - Fill up the information. For now, you may use any name you wish
  - Click on 'Create and Continue'

Step 3: Open project in Android Studio
--------------------
  - Open Android Studio. Select 'Open an existing Android Studio Project'
  - Select the Playphone Sample folder, depending on where you cloned the repository

Step 4: Replace Secret Key and License Key
--------------------
- Secret Key
  - Login to [Playphone Developer Portal](http://developer.playphone.com) with your Playphone Developer account
  - On the left navigation bar, click on 'Profile' tab
  - At the top navigation tabs, click on 'Company Information'
  - Find and copy the 'Secret Key'
  - In `MainActivity.java`, find the following line of code and place your Secret Key within the quotes.  
  ```String secretKey = "REPLACE_WITH_YOUR_SECRET_KEY";```
- License Key
  - Login to [Playphone Developer Portal](http://developer.playphone.com) with your Playphone Developer account
  - On the left navigation bar, click on 'My Games' tab
  - Click into the game that you added in Step 2 above
  - Go to XXX
  - Find and copy the 'Playphone Public License Key'
  - In `BillingActivity.java`, find the following line of code and place your Playphone License Key within the quotes.  
  ```base64EncodedPublicKey = "CONSTRUCT_YOUR_PLAYPHONE_KEY_AND_PLACE_IT_HERE";```
  - *(Optional) If you have used Google's `TrivialDrive` sample before and you have a Google License Key, you can find the following line of code and place your Google License Key within the quotes.*  
  ```base64EncodedPublicKey = "CONSTRUCT_YOUR_GOOGLE_KEY_AND_PLACE_IT_HERE";```

Step 5: Change package name
--------------------
  - Change directory path
    - In the project folder, navigate to `app/src/main/java` 
    - The current directory path under `java` is `com/playphone/sdk3sample` which means the package name is currently `com.playplayphone.sdk3sample`
    - Rename the corresponding folders to reflect your new package name. For example, `com.yourcompanyname.awesomegame`
  - Replace all occurrences of existing package name
    - In Android Studio, go to your Android manifest file and locate the following line  
    ```package="com.playphone.sdk3sample" ```
    - Highlight the entire package name within the quotes
    - Click on Edit -> Find -> Replace in path... *(or hit SHIFT+CMD+R or SHIFT+CTRL+R)*
    - Type in your new package name e.g. `com.yourcompanyname.awesomegame` and click 'Find'
    - Wait for Android Studio to finish searching
    - Once done, click on 'All Files' in the pop-up dialog
  - Change `build.gradle` file
    - Navigate to `app/src`
    - In your `build.gradle` file, change the `applicationId` to your new package name
  - Invalidate caches and restart Android Studio
    - Click on File -> Invalidate Caches / Restart...
    - Click on 'Invalidate and Restart'

Step 6: Add In-app Products
--------------------
  - Add Gas
    - Login to [Playphone Developer Portal](http://developer.playphone.com) with your Playphone Developer account
    - On the left navigation bar, click on 'My Games' tab
    - Click into the game that you added in Step 2 above
    - Go to the 'In-app Products' tab and click on 'Add In-app Product'
    - Choose **Consumable** for this product, and make sure the **Google Play ID/Name is set to 'gas'**
    - Fill in the rest of the details as you wish, and click on 'Save and Continue'
  - Add Premium
    - Follow the same steps as you did for the 'gas' item above, with the following exception: Choose **Durable** for this product, and make sure the **Google Play ID/Name is set to 'premium'**
    - Fill in the rest of the details as you wish, and click on 'Save and Continue'
  - Add Subscription
    - Follow the same steps as you did for the 'gas' and 'premium' items above, with the following exception: Choose **Subscription** for this product, and make sure the **Google Play ID/Name is set to 'infinite_gas'**
    - Fill in the rest of the details as you wish, and click on 'Save and Continue'  
  
**Now that you have finished adding all the In-app products, you are ready to test Billing in the sample app.**
  - Upload your APK
    - Login to [Playphone Developer Portal](http://developer.playphone.com) with your Playphone Developer account
    - On the left navigation bar, click on 'My Games' tab
    - Click into the game that you added in Step 2 above
    - Go to the 'Manage APK' tab and follow the steps to add a new APK file
  - Make sure you already have the Playphone Game Store installed on your testing device. If you do not have the Playphone Game Store installed, you may download it at http://playphone.com
  - Run the sample app and try to purchase a few items

Step 7: Add Leaderboards
--------------------
  - Login to [Playphone Developer Portal](http://developer.playphone.com) with your Playphone Developer account
  - On the left navigation bar, click on 'My Games' tab
  - Go to the 'Leaderboards' tab and click on 'Add Leaderboard'
  - Fill in the details as you wish, and click on the 'Save and Continue' button
  - Take note of the automatically generated **leaderboard ID**  
  
**Now that you have added a leaderboard, you are ready to test Leaderboards in the sample app.**
  - Manually type in the leaderboard ID and the score that you wish to post
  - Tap 'Update Score' to post your score
  - Tap 'Refresh Scores' to fetch the latest rankings

Step 8: Add Achievements
--------------------
  - Login to [Playphone Developer Portal](http://developer.playphone.com) with your Playphone Developer account
  - On the left navigation bar, click on 'My Games' tab
  - Go to the 'Achievements' tab and click on 'Add Achievement'
  - Fill in the details as you wish, and click on the 'Save and Continue' button
  - Take note of the automatically generated **achievement ID**  
  
**Now that you have added an achievement, you are ready to test Achievements in the sample app.**
  - Manually type in the achievement ID and tap on 'Unlock Achievement!'

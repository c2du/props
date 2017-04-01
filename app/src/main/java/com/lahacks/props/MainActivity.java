package com.lahacks.props;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.estimote.sdk.SystemRequirementsChecker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        // SystemRequirementsChecker.check(this);
        // Won't trigger dialogues. With checkWithDefaultDialogs, might want to
        // notify the user why your app needs location and Bluetooth, and what's
        // in it for the user

        /* Note: With the Estimote Android SDK, you can tweak the responsiveness
        of monitoring via the BeaconManager#setBackgroundScanPeriod method.
        By default, monitoring invokes scanning in 25-second intervals.
        Shortening this pause between scans will increase the responsiveness,
        but this comes at the expense of draining more of the smartphone’s battery.
         */

        /* Beacons are id'd by three values:
        UUID, most commonly represented as a string, e.g. “B9407F30-F5F8-466E-AFF9-25556B57FE6D”,
        major number, an unsigned short integer, i.e., an integer ranging from 1 to 65535, (0 is a reserved value)
        minor number, also an unsigned short integer, like the major number.
         */
    }
}

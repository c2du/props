package com.lahacks.props;

import android.app.Activity;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MainActivity extends ListActivity {

    private BeaconManager beaconManager;
    private Region region;
    private ListView mlistview;

    ListView list;
    String[] itemname ={
            "John is nearby...",
            "Sarah is nearby..."
    };

    Integer[] imgid={
            R.drawable.ic_john,
            R.drawable.ic_sarah_small
    };

    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mlistview = (ListView) findViewById(R.id.list);
//        adapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, listItems);
//        mlistview.setAdapter(adapter);

//        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
//                // When clicked, show a toast with the clicked text
//                Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        CustomListAdapter performerAdapter=new CustomListAdapter(this, itemname, imgid);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(performerAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= itemname[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });

        beaconManager = new BeaconManager(this);

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {

                if (!list.isEmpty()) {
                    listItems.clear();
                    adapter.notifyDataSetChanged();

                    for (int i = 0; i < list.size(); i++) {
                        Beacon nextBeacon = list.get(i);
                        listItems.add(nextBeacon.getMajor() + ", " +  nextBeacon.getMinor());
                        Log.d("Airport", nextBeacon.toString());
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        });

        region = new Region("ranged region",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
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

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);

        super.onPause();
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}

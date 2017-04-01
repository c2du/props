package com.lahacks.props;

import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends ListActivity {

    private BeaconManager beaconManager;
    private Region region;

    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private static final Map<String, List<String>> PLACES_BY_BEACONS;

    static {
        Map<String, List<String>> placesByBeacons = new HashMap<>();
        placesByBeacons.put("27539:50765", new ArrayList<String>() {{
            add("Cacao Babao");
            // read as: "Heavenly Sandwiches" is closest
            // to the beacon with major 22504 and minor 48827
            //add("Green & Green Salads");
            // "Green & Green Salads" is the next closest
            //add("Mini Panini");
            // "Mini Panini" is the furthest away
        }});
        /*placesByBeacons.put("648:12", new ArrayList<String>() {{
            add("Mini Panini");
            add("Green & Green Salads");
            add("Heavenly Sandwiches");
        }});*/
        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //closest = (TextView) findViewById(R.id.display_closest);

        adapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);

        beaconManager = new BeaconManager(this);

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {

                if (!list.isEmpty()) {
                    listItems.clear();
                    adapter.notifyDataSetChanged();
                    //showNotification("Props", "Beacons nearby");
                    for (int i = 0; i < list.size(); i++) {
                        Beacon nextBeacon = list.get(i);
                        listItems.add(nextBeacon.getMajor() + ", " +  nextBeacon.getMinor());
                        Log.d("Airport", nextBeacon.toString());
                    }
                    adapter.notifyDataSetChanged();
                    //Beacon nearestBeacon = list.get(0);
                    //Log.d("Airport", String.valueOf(list.size()));
                    //List<String> places = placesNearBeacon(nearestBeacon);

                    // update the UI here
                    //closest.setText(nearestBeacon.toString());
                    //Log.d("Airport", nearestBeacon.toString());
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

    private List<String> placesNearBeacon(Beacon beacon) {
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
            return PLACES_BY_BEACONS.get(beaconKey);
        }
        return Collections.emptyList();
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

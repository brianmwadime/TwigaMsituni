package com.mozilla.hackathon.twigamsituni.datausage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mozilla.hackathon.twigamsituni.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class TrafficStatActivity extends AppCompatActivity {

    TextView latest_rx=null;
    TextView latest_tx=null;
    TextView previous_rx=null;
    TextView previous_tx=null;
    TextView delta_rx=null;
    TextView delta_tx=null;
    TrafficSnapshot latest=null;
    TrafficSnapshot previous=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_stat);
        latest_rx=(TextView)findViewById(R.id.latest_rx);
        latest_tx=(TextView)findViewById(R.id.latest_tx);
        previous_rx=(TextView)findViewById(R.id.previous_rx);
        previous_tx=(TextView)findViewById(R.id.previous_tx);
        delta_rx=(TextView)findViewById(R.id.delta_rx);
        delta_tx=(TextView)findViewById(R.id.delta_tx);

        takeSnapshot(null);
    }

    public void takeSnapshot(View v) {
        previous=latest;
        latest=new TrafficSnapshot(this);

        latest_rx.setText(String.valueOf(latest.device.rx));
        latest_tx.setText(String.valueOf(latest.device.tx));

        if (previous!=null) {
            previous_rx.setText(String.valueOf(previous.device.rx));
            previous_tx.setText(String.valueOf(previous.device.tx));

            delta_rx.setText(String.valueOf(latest.device.rx-previous.device.rx));
            delta_tx.setText(String.valueOf(latest.device.tx-previous.device.tx));
        }

        ArrayList<String> log=new ArrayList<String>();
        HashSet<Integer> intersection=new HashSet<Integer>(latest.apps.keySet());

        if (previous!=null) {
            intersection.retainAll(previous.apps.keySet());
        }

        for (Integer uid : intersection) {
            TrafficRecord latest_rec=latest.apps.get(uid);
            TrafficRecord previous_rec=
                    (previous==null ? null : previous.apps.get(uid));

            emitLog(latest_rec.tag, latest_rec, previous_rec, log);
        }

        Collections.sort(log);

        for (String row : log) {
            Log.d("TrafficMonitor", row);
        }
    }

    private void emitLog(CharSequence name, TrafficRecord latest_rec,
                         TrafficRecord previous_rec,
                         ArrayList<String> rows) {
        if (latest_rec.rx>-1 || latest_rec.tx>-1) {
            StringBuilder buf=new StringBuilder(name);

            buf.append("=");
            buf.append(String.valueOf(latest_rec.rx));
            buf.append(" received");

            if (previous_rec!=null) {
                buf.append(" (delta=");
                buf.append(String.valueOf(latest_rec.rx-previous_rec.rx));
                buf.append(")");
            }

            buf.append(", ");
            buf.append(String.valueOf(latest_rec.tx));
            buf.append(" sent");

            if (previous_rec!=null) {
                buf.append(" (delta=");
                buf.append(String.valueOf(latest_rec.tx-previous_rec.tx));
                buf.append(")");
            }

            rows.add(buf.toString());
        }
    }
}
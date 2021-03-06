package com.mozilla.hackathon.twigamsituni;

import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mozilla.hackathon.twigamsituni.domain.Session;
import com.mozilla.hackathon.twigamsituni.utils.Helpers;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session session = (Session)getApplication();
        session.setInstalledPackages(getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS));

        setContentView(R.layout.activity_main);
        TextView textView = (TextView)findViewById(R.id.first);
        textView.setText(String.valueOf(Helpers.getAppPermissions("com.olx.olx",this)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

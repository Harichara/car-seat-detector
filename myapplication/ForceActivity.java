package theoneandonly.com.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.io.IOException;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.utils.Async;

public class ForceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ParticleDevice mDevice;
    private ImageSwitcher imageSwitcher;
    private TextView textView;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MediaPlayer alertSound = MediaPlayer.create(this, R.raw.alert);

        button2 = (Button) findViewById(R.id.button2);

        textView = (TextView) findViewById(R.id.textView3);

        imageSwitcher = (ImageSwitcher) findViewById(R.id.imgSwitch);

        button2.setVisibility(View.INVISIBLE);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        });

        Async.executeAsync(ParticleCloud.get(getApplicationContext()), new Async.ApiWork<ParticleCloud, Object>() {

            private int forceVal;

            @Override
            public Object callApi(@NonNull ParticleCloud sparkCloud) throws ParticleCloudException, IOException {
                mDevice = sparkCloud.getDevice("YOUR_DEVICE_ID");

                try {
                    forceVal = mDevice.getIntVariable("forceValue");
                } catch (ParticleDevice.VariableDoesNotExistException e) {
                    System.out.println("Error reading variable");
                }

                return -1;
            }

            @Override
            public void onSuccess(@NonNull Object value) {
                button2.setVisibility(View.VISIBLE);
                findViewById(R.id.progressBar7).setVisibility(View.GONE);

                if(forceVal >= 1800) {
                    textView.setText("Not in seat\n\nCHECK CHILD!");
                    imageSwitcher.setImageResource(R.drawable.upsetkid);
                    alertSound.start();
                } else {
                    textView.setText("In seat");
                    imageSwitcher.setImageResource(R.drawable.successkid);
                }
            }

            @Override
            public void onFailure(@NonNull ParticleCloudException e) {
                e.printStackTrace();
                Log.d("info", e.getBestMessage());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForceActivity.this, ForceActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.force, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intentForce = new Intent(ForceActivity.this, ForceActivity.class);
        Intent intentMaps = new Intent(ForceActivity.this, MapsActivity.class);
        Intent intentTemp = new Intent(ForceActivity.this, TempActivity.class);
        Intent intentMain = new Intent(ForceActivity.this, Main4Activity.class);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_force) {
            startActivity(intentForce);
        } else if (id == R.id.nav_maps) {
            startActivity(intentMaps);
        } else if (id == R.id.nav_temp) {
            startActivity(intentTemp);
        } else if (id == R.id.nav_main) {
            startActivity(intentMain);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

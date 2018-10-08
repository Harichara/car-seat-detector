package theoneandonly.com.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.devicesetup.ParticleDeviceSetupLibrary;
import io.particle.android.sdk.utils.Async;
import io.particle.android.sdk.utils.Toaster;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        final TextView userName = (TextView) findViewById(R.id.userName);
        final TextView password = (TextView) findViewById(R.id.password);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                userName.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);

                Async.executeAsync(ParticleCloud.get(getApplicationContext()), new Async.ApiWork<ParticleCloud, Object>() {

                    @Override
                    public Object callApi(@NonNull ParticleCloud sparkCloud) throws ParticleCloudException, IOException {
                        sparkCloud.logIn(userName.getText().toString(), password.getText().toString());
                        sparkCloud.getDevices();
                        mDevice = sparkCloud.getDevice("YOUR_DEVICE_ID");

                        return -1;
                    }

                    @Override
                    public void onSuccess(@NonNull Object value) {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        Intent intent = new Intent(MainActivity.this, Main4Activity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(@NonNull ParticleCloudException e) {
                        Toaster.l(MainActivity.this, e.getBestMessage());
                        e.printStackTrace();
                        Log.d("info", e.getBestMessage());
                    }
                });
            }
        });
    }
    }
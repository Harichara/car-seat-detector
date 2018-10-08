package theoneandonly.com.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.devicesetup.ParticleDeviceSetupLibrary;
import io.particle.android.sdk.utils.Async;
import io.particle.android.sdk.utils.Toaster;

public class DisplayForceActivity extends MainActivity {

    private ParticleDevice mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_force);

        Button button3 = (Button) findViewById(R.id.button3);
        //final TextView textYes = (TextView) findViewById(R.id.textView2);
        //final TextView textNo = (TextView) findViewById(R.id.textView3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Async.executeAsync(ParticleCloud.get(getApplicationContext()), new Async.ApiWork<ParticleCloud, Object>() {
//
//                    private int forceVal;
//
//                    @Override
//                    public Object callApi(@NonNull ParticleCloud sparkCloud) throws ParticleCloudException, IOException {
//                        mDevice = sparkCloud.getDevice("37004b000b51343334363138");
//
//                        try {
//                            forceVal = mDevice.getIntVariable("forceValue");
//                        } catch (ParticleDevice.VariableDoesNotExistException e) {
//                            System.out.println("Error reading variable");
//                        }
//
//                        return -1;
//                    }
//
//                    @Override
//                    public void onSuccess(@NonNull Object value) {
//                        if(forceVal > 3500) {
//                        } else {
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull ParticleCloudException e) {
//                        e.printStackTrace();
//                        Log.d("info", e.getBestMessage());
//                    }
//                });

//                try {
//                    Toaster.l(DisplayForceActivity.this, String.valueOf(test.getmDevice().getIntVariable("forceValue")));
//                } catch (ParticleCloudException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    Toaster.l(DisplayForceActivity.this, "Error 1");
//                } catch (ParticleDevice.VariableDoesNotExistException e) {
//                    Toaster.l(DisplayForceActivity.this, "Error 2");
//                }
                //Intent intent = new Intent(DisplayForceActivity.this, MapsActivity.class);
                //startActivity(intent);
            }
        });
    }
}

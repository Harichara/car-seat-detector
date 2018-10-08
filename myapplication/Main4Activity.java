package theoneandonly.com.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class Main4Activity extends AppCompatActivity {

    private ParticleDevice mDevice;
    private ImageSwitcher imageSwitcher;
    private TextView textView;
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        final MediaPlayer alertSound = MediaPlayer.create(this, R.raw.alert);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imgSwitch);
        textView = (TextView) findViewById(R.id.textView3);
        button = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button5);

        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);

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
            private double temp;

            @Override
            public Object callApi(@NonNull ParticleCloud sparkCloud) throws ParticleCloudException, IOException {
                mDevice = sparkCloud.getDevice("YOUR_DEVICE_ID");

                try {
                    forceVal = mDevice.getIntVariable("forceValue");
                    temp = mDevice.getDoubleVariable("temp");
                } catch (ParticleDevice.VariableDoesNotExistException e) {
                    System.out.println("Error reading variable");
                }

                return -1;
            }

            @Override
            public void onSuccess(@NonNull Object value) {
                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);

                if (forceVal <= 1800 && temp >= 0.80) {
                    imageSwitcher.setImageResource(R.drawable.upsetkid);
                    textView.setText("TEMPERATURE TOO HOT!!!\n\nCHECK CHILD");
                    alertSound.start();
                } else if (forceVal <= 1800 && temp <= 0.60) {
                    imageSwitcher.setImageResource(R.drawable.upsetkid);
                    textView.setText("TEMPERATURE TOO COLD!!!\n\nCHECK CHILD");
                    alertSound.start();
                } else if (forceVal > 1800) {
                    imageSwitcher.setImageResource(R.drawable.upsetkid);
                    textView.setText("CHILD NOT IN SEAT!!!\n\nCHECK CHILD");
                    alertSound.start();
                } else {
                    imageSwitcher.setImageResource(R.drawable.successkid);
                    textView.setText("CHILD IS SAFE!");
                }
            }

            @Override
            public void onFailure(@NonNull ParticleCloudException e) {
                e.printStackTrace();
                Log.d("info", e.getBestMessage());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main4Activity.this, Main4Activity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main4Activity.this, ForceActivity.class);
                startActivity(intent);
            }
        });
    }
}

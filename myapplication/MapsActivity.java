package theoneandonly.com.myapplication;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.utils.Async;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private GoogleMap mMap;
    private ParticleDevice mDevice;
    private GoogleApiClient mGoogleApiClient;
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
        }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Button button = (Button) findViewById(R.id.findDevice);
        aSwitch = (Switch) findViewById(R.id.switch2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();

                Async.executeAsync(ParticleCloud.get(getApplicationContext()), new Async.ApiWork<ParticleCloud, Object>() {

                    private double lat;
                    private double lon;

                    @Override
                    public Object callApi(@NonNull ParticleCloud sparkCloud) throws ParticleCloudException, IOException {
                        mDevice = sparkCloud.getDevice("YOUR_DEVICE_ID");

                        try {
                            lat = mDevice.getDoubleVariable("lat");
                        } catch (ParticleDevice.VariableDoesNotExistException e) {
                            System.out.println("Error reading latitude variable");
                        }

                        try {
                            lon = mDevice.getDoubleVariable("lon");
                        } catch (ParticleDevice.VariableDoesNotExistException e) {
                            System.out.println("Error reading longitude variable");
                        }

                        return -1;
                    }

                    @Override
                    public void onSuccess(@NonNull Object value) {
                        LatLng location = new LatLng(lat, lon);
                        mMap.addMarker(new MarkerOptions().position(location)).setTitle("Child Is Here!");
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 20));
                    }

                    @Override
                    public void onFailure(@NonNull ParticleCloudException e) {
                        e.printStackTrace();
                        Log.d("info", e.getBestMessage());
                    }
                });
            }
        });

        aSwitch.setChecked(false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}

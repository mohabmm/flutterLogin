package info.androidhive.recaptcha;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.safetynet.SafetyNet;
import com.scottyab.safetynet.SafetyNetHelper;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {




//1) define the button sign in sha1
    // link to put sha1 to complete recording :   https://console.developers.google.com/apis/credentials/key?project=lllll-199317

    // a variable holds safety net name
    //  our api key we register for
    private static final String API_KEY = "AIzaSyCvHp4fLetu-GsRj0oQGftouGvIpUQt_P0";
    public SafetyNetHelper safetyNetHelper;
    Button safebrowsingbutton;// number one to start with
    Button verifyappsbutton; // third one


    // here is object of a class GoogleApiclient
    Button Attestation_button; // forth one
    TextView tvResult;
    private GoogleApiClient mGoogleApiClient;
    // the key used for Recaptcha send as a paramter
    // final String SiteKey = "6LeJVkoUAAAAAEpUe9sphAsT-5zAJnBM6NGPy_8W";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyappsbutton = (Button) findViewById(R.id.verify_safety_apps_button);
        safebrowsingbutton = (Button) findViewById(R.id.safe_browsing_button);
        safetyNetHelper = new SafetyNetHelper(API_KEY);
        Attestation_button = findViewById(R.id.safety_net_button);

        // safety net verify apps test :
        // here we use safety net helper library
        // we use ready mde library in our android apps so it can help us in some concepts
        // earlier we call an object from the library class we use it down and send it our api key which
        // is special for our app


        verifyappsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), Verifyapp.class);
                startActivity(intent);
                //Determine whether app verification is enabled
                //The asynchronous isVerifyAppsEnabled() method allows your app to determine whether the user is enrolled in the Verify Apps feature.

            }
        });

        // the first one to implement
        safebrowsingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RequestSafeBrowsing.class);
                startActivity(intent);

            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .build();

        mGoogleApiClient.connect();


        Attestation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), RequestAttestation.class);
                startActivity(intent);


            }
        });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}

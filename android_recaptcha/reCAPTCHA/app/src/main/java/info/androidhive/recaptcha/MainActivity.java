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



/*

        the goals of our app :
        SafetyNet Attestation_button API
        Learn how the SafetyNet Attestation_button API provides services for determining whether a device running your app satisfies Android compatibility tests.
        SafetyNet Safe Browsing API
        Learn how the SafetyNet Safe Browsing API provides services for determining whether a URL has been marked as a known threat by Google.
        SafetyNet reCAPTCHA API
        Learn how the SafetyNet reCAPTCHA API protects your app from malicious traffic.
        SafetyNet Verify Apps API
        Learn how the SafetyNet Verify Apps API protects devices against potentially harmful apps.



the reason for using Safety net:
The SafetyNet Attestation_button API helps you assess the security and compatibility of the Android environments in which your apps run
. You can use this API to analyze devices that have installed your app.






 the reason for using SafetyNet Safe Browsing API:
SafetyNet provides services for determining whether a URL has been marked as a known threat by Google.
Your app can use this API to determine whether a particular URL has been classified by Google as a known threat. Internally, SafetyNet implements a client for the Safe Browsing Network Protocol v4 developed by Google. Both the client code and the v4 network protocol were designed to preserve users' privacy and keep battery and bandwidth consumption to a minimum. Use this API to take full advantage of Google's Safe Browsing service on Android in the most resource-optimized way,
and without implementing its network protocol.
This document explains how to use SafetyNet to check a URL for known threats.
To use the Safe Browsing API, you must initialize the API by calling initSafeBrowsing() and waiting for it to complete.
The following code snippet provides an example:
Tasks.await(SafetyNet.getClient(this).initSafeBrowsing());



 the reason for using SafetyNet reCAPTCHA API:
The SafetyNet service includes a reCAPTCHA API that you can use to protect your app from malicious traffic.
reCAPTCHA is a free service that uses an advanced risk analysis engine to protect your app from spam and other abusive actions.
If the service suspects that the user interacting with your app might be a bot instead of a human,
 it serves a CAPTCHA that a human must solve before your app can continue executing.





the reason for choosing SafetyNet Verify Apps API:
The SafetyNet Verify Apps API allows your app to interact programmatically with the Verify Apps feature on a device,
 protecting the device against potentially harmful apps.
*/


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


// An activity is the entry point for interacting with the user. It represents a single screen with a user interface. For example
// , an email app might have one activity that shows a list of new emails,
// another activity to compose an email, and another activity for reading emails.
// Although the activities work together to form a cohesive user experience in the email app, each one is independent of the others.
// As such, a different app can start any one of these activities if the email app allows it.
// For example, a camera app can start the activity in the email app that composes new mail to allow the user to share a picture.




/*
that why we use app compactivity :
Base class for activities that use the support library action bar features.
You can add an ActionBar to your activity when running on API level 7 or higher
 by extending this class for your activity and setting the activity theme to Theme.AppCompat
 or a similar theme.
*/


/*
 the reason for using implements :


 In object-oriented programming, inheritance is when an object or class is based on another object
   class (class-based inheritance), using the same implementation. ... Still
 , inheritance is a commonly used mechanism for establishing subtype relationships.
  here we depend on Google api client

  the . (dot) after the Class (Google client ) is used to acsses two functions we work on
  when the connection is succefully , and listner to listen if the conncetion failed

*/


//1) start with defining the button sign in sha1
    // link to put shai to complete recording :   https://console.developers.google.com/apis/credentials/key?project=lllll-199317

    // a variable holds safety net name
    //  our api key we register for
    private static final String API_KEY = "AIzaSyCvHp4fLetu-GsRj0oQGftouGvIpUQt_P0";
    public SafetyNetHelper safetyNetHelper;
    Button safebrowsingbutton;// number one to start with
    Button verifyappsbutton; // third one


    // here is object of a class GoogleApiclient
     /*
        we need to know what is object :
      Executable entity combining code and data
      Code is organised into methods
      Data is accessed via the methods

   mGoogleApiClient is our object of Class  GoogleApiClient

  so what is a Class :

  Template or factory for creating objects
  called "instances" of the class
  Has definitions of methods and data
  Supports inheritance from other classes
  hence "class hierarchy"

    */
    //thats why we got no error found in safe browsing :
    // I can see why the Transparency Report wording and Safe Browsing API responses appear to contradict one another.
    // The Transparency Report communicates the extent to which the provided site is bad; in this case,
    // the site is only "partially" bad ("Some pages on this site..."). The Safe Browsing API, however,
    // will only return a verdict when the provided URL is definitively bad;
    // i.e. we have determined that all URLs (including the root domain) are not unsafe for a user to access.
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
        // here we use safety net helper libirary
        // we use ready mde libirary in our android apps so it can help us in some concepts
        // earlier we call an object from the libirary class we use it down and send it our api key whitch
        // is special for our app


        verifyappsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), Verifyapp.class);
                startActivity(intent);
                //Determine whether app verification is enabled
                //The asynchronous isVerifyAppsEnabled() method allows your app to determine whether the user is enrolled in the Verify Apps feature.
                // This method returns a VerifyAppsUserResult object, which contains information regarding all actions that
                // the user has taken related to the Verify Apps feature, including enabling it.

                // The following code snippet shows how to create the callback associated with this method:


            }
        });


        // the first one to implement
        safebrowsingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), Requestsafebrwosing.class);
                startActivity(intent);

            }
        });


        //You can use the GoogleApiClient ("Google API Client") object to access the Google APIs provided in the Google Play
        //services library (such as Google Sign-In, Games, and Drive). ... Automatically manage your connection to Google Play services.

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


    // @SuppressLint("StaticFieldLeak")

    //AsyncTask enables proper and easy use of the UI thread.
    //This class allows you to perform background operations and publish results on the UI thread without having to manipulate threads and/or handlers.

    // The three types used by an asynchronous task are the following:

    // Params, the type of the parameters sent to the task upon execution.
    //       Progress, the type of the progress units published during the background computation.
    //     Result, the type of the result of the background computation.

    //  Not all types are always used by an asynchronous task. To mark a type as unused, simply use the type Void:

    //  private class MyTask extends AsyncTask<Void, Void, Void> { ... }



    /*



    When an asynchronous task is executed, the task goes through 4 steps:

onPreExecute(), invoked on the UI thread before the task is executed. This step is normally
 used to setup the task, for instance by showing a progress bar in the user interface.
doInBackground(Params...), invoked on the background thread immediately after onPreExecute() finishes executing.
 This step is used to perform background computation that can take a long time.
 The parameters of the asynchronous task are passed to this step.
  The result of the computation must be returned by this step and will be passed back to the last step.
  This step can also use publishProgress(Progress...) to publish one or more units of progress
  . These values are published on the UI thread, in the onProgressUpdate(Progress...) step.
onProgressUpdate(Progress...), invoked on the UI thread after a call to publishProgress(Progress...)
. The timing of the execution is undefined.
 This method is used to display any form of progress in the user interface while the background computation is still executing.
  For instance, it can be used to animate a progress bar or show logs in a text field.
onPostExecute(Result), invoked on the UI thread after the background computation finishes.
 The result of the background computation is passed to this step as a parameter.
    * */



    /*

SafetyNet Attestation_button API result :

    If the value of ctsProfileMatch is true,
     then the profile of the device running your app matches the profile of a device that has passed Android compatibility testing.

   If the value of basicIntegrity is true, then the device running your app likely wasn't tampered with,
   but the device hasn't necessarily passed Android compatibility testing.



    * */
}

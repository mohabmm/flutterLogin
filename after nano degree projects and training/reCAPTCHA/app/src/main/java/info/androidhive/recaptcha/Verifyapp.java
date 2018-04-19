package info.androidhive.recaptcha;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.safetynet.HarmfulAppsData;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.safetynet.SafetyNetClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;


public class Verifyapp extends AppCompatActivity {


    Button verifyappbutton;
    EditText verify_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifyapp);
        verify_result = (EditText) findViewById(R.id.verify_result);
        verifyappbutton = (Button) findViewById(R.id.verifyappsbutton);
        verifyappbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SafetyNet.getClient(getApplicationContext())
                        .isVerifyAppsEnabled()
                        .addOnCompleteListener(new OnCompleteListener<SafetyNetApi.VerifyAppsUserResponse>() {
                            @Override
                            public void onComplete(Task<SafetyNetApi.VerifyAppsUserResponse> task) {
                                if (task.isSuccessful()) {
                                    SafetyNetApi.VerifyAppsUserResponse result = task.getResult();
                                    if (result.isVerifyAppsEnabled()) {
                                        Log.d("MY_APP_TAG", "The Verify Apps feature is enabled.");
                                        //   Toast.makeText(getApplicationContext(),"The Verify Apps feature is enabled",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("MY_APP_TAG", "The Verify Apps feature is disabled.");
                                        // Toast.makeText(getApplicationContext(),"The Verify Apps feature is disabled",Toast.LENGTH_SHORT).show();

                                    }
                                } else {
                                    Log.e("MY_APP_TAG", "A general error occurred.");
                                }
                            }
                        });


                //    Request enabling of app verification
                //   The asynchronous enableVerifyApps() method allows your app to invoke a dialog requesting that the user enable the Verify Apps feature.
                // This method returns a VerifyAppsUserResult object,
                // which contains information regarding all actions that the user has taken related to the Verify Apps feature,
                // including whether they've given consent to enable it.

                //     The following code snippet shows how to create the callback associated with this method:


                SafetyNet.getClient(getApplicationContext())
                        .enableVerifyApps()
                        .addOnCompleteListener(new OnCompleteListener<SafetyNetApi.VerifyAppsUserResponse>() {
                            @Override
                            public void onComplete(Task<SafetyNetApi.VerifyAppsUserResponse> task) {
                                if (task.isSuccessful()) {
                                    SafetyNetApi.VerifyAppsUserResponse result = task.getResult();
                                    if (result.isVerifyAppsEnabled()) {
                                        Log.d("MY_APP_TAG", "The user gave consent " +
                                                "to enable the Verify Apps feature.");
                                    } else {
                                        Log.d("MY_APP_TAG", "The user didn't give consent " +
                                                "to enable the Verify Apps feature.");
                                    }
                                } else {
                                    Log.e("MY_APP_TAG", "A general error occurred.");
                                }
                            }
                        });


                SafetyNetClient safetyNetClient = SafetyNet.getClient(getApplicationContext());

                safetyNetClient.listHarmfulApps()
                        .addOnCompleteListener(new OnCompleteListener<SafetyNetApi.HarmfulAppsResponse>() {
                            @Override

                            //Task<TResult>	Represents an asynchronous operation.
                            //Task is a class
                            public void onComplete(@NonNull Task<SafetyNetApi.HarmfulAppsResponse> task) {


                                if (task.isSuccessful()) {
                                    SafetyNetApi.HarmfulAppsResponse result = task.getResult();

                                    List<HarmfulAppsData> appList = result.getHarmfulAppsList();
                                    if (appList.isEmpty()) {
                                        Log.d("FragmentSafetyNet", "There are no known potentially harmful apps installed.");
                                        verify_result.setText("There are no known potentially harmful apps installed");
                                    } else {

                                        Log.e("FragmentSafetyNet", "Potentially harmful apps are installed!");
                                        verify_result.setText("Potentially harmful apps are installed!");


                                        for (HarmfulAppsData harmfulApp : appList) {
                                            Log.e("SafetyNet", "Information about a harmful app:");
                                            Log.e("SafetyNet", "  APK: " + harmfulApp.apkPackageName);
                                            Log.e("SafetyNet", "  SHA-256: " + harmfulApp.apkSha256);
                                            Log.e("SafetyNet", "  Category: " + harmfulApp.apkCategory);
                                        }
                                    }
                                } else {

                                    verify_result.setText("An error occured during the operation  ");


                                }
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.HarmfulAppsResponse>() {
                            @Override
                            public void onSuccess(SafetyNetApi.HarmfulAppsResponse harmfulAppsResponse) {
                                Log.d("listHarmfulApps()", "Sucess! Received listHarmfulApps() result");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("listHarmfulApps()", "Error on failure lisner lisner has an error : " + e.getMessage());
                            }
                        });

            }
        });


    }

}

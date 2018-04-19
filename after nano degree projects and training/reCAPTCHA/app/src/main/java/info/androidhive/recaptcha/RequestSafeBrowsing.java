package info.androidhive.recaptcha;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class RequestSafeBrowsing extends AppCompatActivity {


    private static final String TAG = "SafetyNetBrowsing";
    private static final String API_KEY = "AIzaSyCvHp4fLetu-GsRj0oQGftouGvIpUQt_P0";
    EditText inserurledit;
    EditText resulurledit;
    Button requesafebrwosing;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safebrowsing);

        inserurledit = (EditText) findViewById(R.id.url_insert);
        resulurledit = (EditText) findViewById(R.id.url_result);
        requesafebrwosing = (Button) findViewById(R.id.safebrwosingbutton);


        requesafebrwosing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url = inserurledit.getText().toString();


                if (TextUtils.isEmpty(url)) {
                    // Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                    resulurledit.setText("please enter url ");

                    return;
                } else if (URLUtil.isValidUrl(url)) {


                    final LoadThreatData loadThreatData = new LoadThreatData();


                    loadThreatData.execute();


                } else {

                    //  Toast.makeText(getApplicationContext(),,Toast.LENGTH_SHORT).show();
                    resulurledit.setText("please enter valid url");
                }


            }
        });


    }


    public class LoadThreatData extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            SafetyNet.getClient(getApplicationContext()).initSafeBrowsing();
            Log.d("Threats", "init");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            //  exampels of malware websites :
            //"go.trackmyclicks202.com";//"http://ianfette.org/";
            // "http://malware.wicar.org/data/eicar.com";


            SafetyNet.getClient(getApplicationContext()).lookupUri(
                    url,
                    API_KEY,
                    com.google.android.gms.safetynet.SafeBrowsingThreat.TYPE_POTENTIALLY_HARMFUL_APPLICATION,
                    com.google.android.gms.safetynet.SafeBrowsingThreat.TYPE_SOCIAL_ENGINEERING)
                    .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.SafeBrowsingResponse>() {
                        @Override
                        public void onSuccess(SafetyNetApi.SafeBrowsingResponse sbResponse) {
                            // Indicates communication with the service was successful.
                            // Identify any detected threats.
                            if (sbResponse.getDetectedThreats().isEmpty()) {
                                // No threats found.
                                Log.e("Threats", "no threats found");

                                resulurledit.setText("no threats found ");

                                //always get in here
                            } else {
                                // Threats found!
                                Log.e("Threats", sbResponse.toString());

                                resulurledit.setText(" threats found ");

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // An error occurred while communicating with the service.
                            if (e instanceof ApiException) {
                                // An error with the Google Play Services API contains some
                                // additional details.
                                ApiException apiException = (ApiException) e;
                                Log.d(TAG, "Error : " + CommonStatusCodes
                                        .getStatusCodeString(apiException.getStatusCode()));


                                resulurledit.setText("error1 " + CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));

                                // Note: If the status code, apiException.getStatusCode(),
                                // is SafetyNetstatusCode.SAFE_BROWSING_API_NOT_INITIALIZED,
                                // you need to call initSafeBrowsing(). It means either you
                                // haven't called initSafeBrowsing() before or that it needs
                                // to be called again due to an internal error.
                            } else {
                                // A different, unknown type of error occurred.
                                Log.d(TAG, "Error: " + e.getMessage());


                                resulurledit.setText("error1 " + e.getMessage());

                            }
                        }
                    });

        }


    }


}

package info.androidhive.recaptcha;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Recaptcha extends AppCompatActivity {

    private static final String TAG = Recaptcha.class.getSimpleName();

    // follow this link to know why it is automatically passed :  https://support.google.com/recaptcha/?hl=en

    private static final String SAFETY_NET_API_SITE_KEY = "6Lf8z0sUAAAAAP80KqD1U-3e7M_JlOrgWSms5XDd";

    //The server
    private static final String URL_VERIFY_ON_SERVER = "https://api.androidhive.info/google-recaptcha-verfication.php";


    // TODO: 4/1/2018    https://www.androidhive.info/2018/03/android-recaptcha-safetynet/(more explaination website)


    Button buttonrecaptcha;
    TextView recaptcharesult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recaptcha);
        recaptcharesult = (TextView) findViewById(R.id.recaptcha_info);

        buttonrecaptcha = (Button) findViewById(R.id.request_recaptcha_button);


        buttonrecaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SafetyNet.getClient(getApplicationContext()).verifyWithRecaptcha(SAFETY_NET_API_SITE_KEY)
                        .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                            @Override
                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                Log.d(TAG, "onSuccess");

                                if (!response.getTokenResult().isEmpty()) {

                                    // Received captcha token
                                    // This token still needs to be validated on the server
                                    // using the SECRET key


                                    verifyTokenOnServer(response.getTokenResult());
                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    ApiException apiException = (ApiException) e;
                                    Log.d(TAG, "Error message: " +
                                            CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));

                                    Toast.makeText(getApplicationContext(), "Error message:" +
                                            CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()), Toast.LENGTH_SHORT).show();

                                } else {
                                    Log.d(TAG, "Unknown type of error: " + e.getMessage());

                                    Toast.makeText(getApplicationContext(), "Unknown type of error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }

        });


    }


    // Showing reCAPTCHA dialog


    /**
     * Verifying the captcha token on the server
     * Post param: recaptcha-response
     * Server makes call to https://www.google.com/recaptcha/api/siteverify
     * with SECRET Key and Captcha token
     */
    public void verifyTokenOnServer(final String token) {
        Log.d(TAG, "Captcha Token" + token);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_VERIFY_ON_SERVER, new Response.Listener<String>() {


            //  Overriding is a feature that allows a subclass or child class
            //  to provide a specific implementation of a method that is already provided by one of its super-classes or parent classes.

            // subclass uses a function of main class      (string request uses function of the main class of it)

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("message");

                    if (success) {
                        // Congrats! captcha verified successfully on server
                        // TODO - submit the feedback to the  server


                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                // recaptcharesult.setText("Error: " +
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("recaptcha-response", token);

                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(strReq);
    }/*
}
*/

}


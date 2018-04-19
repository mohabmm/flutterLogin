package info.androidhive.recaptcha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.scottyab.safetynet.SafetyNetHelper;


public class RequestAttestation extends AppCompatActivity {

    Button attestation;
    EditText ctc;
    EditText basicintegrity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attestation);

        attestation = (Button) findViewById(R.id.attestationapp_button);
        ctc = (EditText) findViewById(R.id.ctc);
        basicintegrity = (EditText) findViewById(R.id.basicintegrity);


        attestation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SafetyNetHelper safetyNetHelper = new SafetyNetHelper("AIzaSyDsYqAB3nN9rLrqNC-SN0bA2MJfkrL7hGw");

                safetyNetHelper.requestTest(getApplicationContext(), new SafetyNetHelper.SafetyNetWrapperCallback() {
                    @Override
                    public void error(int errorCode, String msg) {
                        //handle and retry depending on errorCode
                        Log.e("api", msg);
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void success(boolean ctsProfileMatch, boolean basicIntegrity) {

                        if (ctsProfileMatch) {
                            //profile of the device running your app matches the profile of a device that has passed Android compatibility testing.

                            ctc.setText("ctc:true");
                            //handle fail, maybe warn user device is unsupported or in compromised state? (this is up to you!)

                        } else {

                            ctc.setText("ctc:false");
                        }
                        if (basicIntegrity) {


                            basicintegrity.setText("basicIntegrity:true");
                            //then the device running your app likely wasn't tampered with, but the device has not necessarily passed Android compatibility testing.
                        } else {
                            basicintegrity.setText("basicIntegrity:false");

                        }


                    }


                });
            }
        });


    }
}

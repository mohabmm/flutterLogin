package main.model;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {


    EditText ediphone, edipassword,ediname;
    Button btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ediphone = findViewById(R.id.ediphones);
        edipassword = findViewById(R.id.edipassword);
        ediname = findViewById(R.id.edtNames);
        btnsignup = findViewById(R.id.btnsigup);

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("please wait ....");
                progressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Check if already user phone

                        if(dataSnapshot.child(ediphone.getText().toString()).exists()){

                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(),"User already exists ",Toast.LENGTH_SHORT).show();


                        }

                        else {


                            progressDialog.dismiss();

                            User user = new User(ediname.getText().toString(),edipassword.getText().toString());
                            table_user.child(ediphone.getText().toString()).setValue(user);

                            Toast.makeText(getApplicationContext(),"Sign Up Successfully",Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}

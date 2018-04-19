package main.model;

import android.app.ProgressDialog;
import android.content.Intent;
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

import main.model.common.Common;


public class Signin extends AppCompatActivity {

    EditText ediphone, edipassword;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ediphone = findViewById(R.id.ediphone);
        edipassword = findViewById(R.id.edipassword);
        signin = findViewById(R.id.btnsigin);

        // here we intiate the database

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
         final DatabaseReference table_user = database.getReference("User");
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(Signin.this);
                progressDialog.setMessage("please wait ....");
                progressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // check if the user not exist in the database

                        if (dataSnapshot.child(ediphone.getText().toString()).exists()) {


                 
                            // get user information


                            progressDialog.dismiss();
                          User user = dataSnapshot.child(ediphone.getText().toString()).getValue(User.class);

                          // the problem is in the user to get password
                            String userpass = user.getPassword();
                          String textnotic = edipassword.getText().toString();


                            if (userpass.equals(textnotic)) {


                                Intent intent = new Intent(Signin.this, Home.class);
                                Common.current_user= user;
                                startActivity(intent);
                                finish();


                            } else {

                                progressDialog.dismiss();
                                Toast.makeText(Signin.this, "Wrong Password ", Toast.LENGTH_SHORT).show();

                            }
                        }else {

                            progressDialog.dismiss();
                            Toast.makeText(Signin.this, "User Did not Exist in the database", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(Signin.this,"database error ",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }
}

package main.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button signup,signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup =  findViewById(R.id.signupbutton);
        signin =  findViewById(R.id.signinbutton);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signinInetnet = new Intent(getApplicationContext(),Signin.class);
                startActivity(signinInetnet);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signinInetnet = new Intent(getApplicationContext(),SignUp.class);
                startActivity(signinInetnet);
            }
        });
    }
}

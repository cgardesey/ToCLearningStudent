package com.prepeez.toclearningstudent.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prepeez.toclearningstudent.R;

public class EmailVerificationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseUser user;

    Button verifyEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        mAuth = FirebaseAuth.getInstance();
        verifyEmail = (Button) findViewById(R.id.verify_email);
        user = mAuth.getCurrentUser();

        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyEmail.setEnabled(false);
                user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(EmailVerificationActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                    finish();
                                }
                                else {
                                    verifyEmail.setEnabled(true);
                                    Toast.makeText(EmailVerificationActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }
}

package com.prepeez.toclearningstudent.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.prepeez.toclearningstudent.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    static public FirebaseAuth mAuth;
    static final String TAG = "LoginActivity";
    TextView mForgotPassword;
    private EditText mEmail, mPassword;
    private Button mLogin, mRegistration;
    private ProgressDialog mProgress;
    String identity;


    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getIntent().getExtras() != null){
            identity = getIntent().getExtras().getString("IDENTITY");
        }

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        mLogin = (Button) findViewById(R.id.login);
        mRegistration = (Button) findViewById(R.id.registration);

        mForgotPassword = (TextView) findViewById(R.id.forgotPassword);

        mAuth = FirebaseAuth.getInstance();

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                startActivity(intent);
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration();
            }
        });

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mProgress.dismiss();
                FirebaseUser user = mAuth.getCurrentUser();
                //Toast.makeText(LoginActivity.this, "onAuthStateChanged", Toast.LENGTH_SHORT).show();
                if(user != null){
                    // User is signed in
                    if (user.isEmailVerified()) {
                        //Toast.makeText(LoginActivity.this, "email is verified", Toast.LENGTH_SHORT).show();
                        String user_id = mAuth.getCurrentUser().getUid();

                        Intent i = new Intent();
                        setResult(Activity.RESULT_OK, i);
                        finish();
                    }
                    else if(!TextUtils.isEmpty(mEmail.getText().toString() + mPassword.getText().toString()) ) {
                        //mEmail.setText(user.getEmail().toString());
                        Intent intent = new Intent(LoginActivity.this, EmailVerificationActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "This email is not verified.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (firebaseAuthListener != null) {
            mAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    private void attemptLogin() {
        // Reset errors.
        mEmail.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        boolean canAttemptLogin = true;
        View focusView = mEmail;

        // Check for a valid password, if the user entered one.


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            canAttemptLogin = false;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            canAttemptLogin = false;
        }

        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            //focusView = mPassword;
            canAttemptLogin = false;
        }
        if (!canAttemptLogin) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            mProgress.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        mProgress.dismiss();
                        if (task.getException() instanceof FirebaseAuthInvalidUserException){
                            Toast.makeText(LoginActivity.this, "This email is not registered on 2C Learning Student!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Sign in error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    private void attemptRegistration() {
        // Reset errors.
        mEmail.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        boolean canAttemptRegistration = true;
        View focusView = mEmail;

        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            canAttemptRegistration = false;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            canAttemptRegistration = false;
        }

        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            //focusView = mPassword;
            canAttemptRegistration = false;
        }
        else if (!isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            canAttemptRegistration = false;
        }
        if (!canAttemptRegistration) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mProgress.dismiss();
//                        Intent intent = new Intent(LoginActivity.this, EmailVerificationActivity.class);
//                        startActivity(intent);
                    }
                    else{
                        mProgress.dismiss();
                        if (task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(LoginActivity.this, "This email is already registered!", Toast.LENGTH_SHORT).show();
                        }
                        else if (task.getException() instanceof FirebaseAuthInvalidUserException){
                            Toast.makeText(LoginActivity.this, "Invalid user!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Sign up error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }
}

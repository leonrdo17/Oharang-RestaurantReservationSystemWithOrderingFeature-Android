package com.leonardo.tableappreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.leonardo.tableappreservation.menu.RestaurantOrderMenu;

public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginButton;
    TextView mRegisterText, mforgotPasswordText;
    FirebaseAuth fAuth;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.loginEmail);
        mPassword = findViewById(R.id.loginPassword);
        fAuth = FirebaseAuth.getInstance();
        mLoginButton = findViewById(R.id.loginButton);
        mRegisterText = findViewById(R.id.registerText);
        mforgotPasswordText = findViewById(R.id.forgetPasswordText);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (email.equals("admin") && password.equals("123456")) {
                    Intent intent = new Intent(getApplicationContext(), OwnerOptions.class);
                    startActivity(intent);

                } else if (TextUtils.isEmpty(email)){
                    mEmail.setError("Please insert the Email.");
                    return;

                } else if (TextUtils.isEmpty(password)){
                    mPassword.setError("Please insert the password.");
                    return;

                } else if (password.length() < 6){
                    mPassword.setError("Password must be more than 6 characters.");
                    return;

                } else {
                    // Authentication

                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Login.this, "Welcome! You have been Logged in to the application.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }else {
                                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        mRegisterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        mforgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText (v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mail = resetMail.getText().toString();




                        if (mail.isEmpty()) {
                            Toast.makeText(Login.this, "Please insert the email", Toast.LENGTH_SHORT).show();
                        } else {

                            fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Login.this, "Reset Link Sent To Your Email.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, "Reset Link is not sent." + e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.create().show();

            }
        });

    }
}

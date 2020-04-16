package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginAccount extends AppCompatActivity {
    private Button login_button;
    private Button create_acc_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__account);
        login_button = findViewById(R.id.email_sign_in_button);
        create_acc_button = findViewById(R.id.create_acc_button);

        create_acc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAccount.this, CreateAccount.class));
            }
        });

    }
}

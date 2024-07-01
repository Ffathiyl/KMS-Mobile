package com.polytechnic.astra.ac.id.knowledgemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.LoginRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity.LoginActivity;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_kms);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String username = usernameEditText.getText().toString().trim();  // Trim untuk menghilangkan spasi tambahan

        LoginRepository loginRepository = LoginRepository.get();
        loginRepository.setUsername(username);
        loginRepository.getLogin().observe(this, new Observer<List<LoginModel>>() {
            @Override
            public void onChanged(List<LoginModel> loginModels) {
                if (loginModels != null && !loginModels.isEmpty()) {
                    // If data is not empty, navigate to MainActivity
                    LoginModel loginModel = loginModels.get(0);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra("LoginModel", loginModel);
                    startActivity(intent);
                    finish();
                } else {
                    // Show error if no data found
                    Log.e("LoginActivity", "No data found");
                }
            }
        });
    }
}

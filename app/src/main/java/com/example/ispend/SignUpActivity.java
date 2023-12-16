package com.example.ispend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ispend.DB.AppDatabase;
import com.example.ispend.DB.iSpendDAO;

public class SignUpActivity extends AppCompatActivity {

    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;
    private Button mSignUpButton;
    private iSpendDAO miSpendDAO;
    private String mUsername;
    private String mPassword;
    private String mConfirmPassword;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        wireUpDisplay();

        getDatabase();

    }

    private void wireUpDisplay() {
        mUsernameField = findViewById(R.id.username_edittext);
        mPasswordField = findViewById(R.id.password_edittext);
        mConfirmPasswordField = findViewById(R.id.confirm_password_edittext);
        mSignUpButton = findViewById(R.id.sign_up_button);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    Toast.makeText(SignUpActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                } else {
                    if(mPassword.equals(mConfirmPassword)){
                        User user = new User(mUsername, mPassword, false);
                        miSpendDAO.insert(user);
                        Toast.makeText(SignUpActivity.this, "User created", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void getValuesFromDisplay() {
        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
        mConfirmPassword = mConfirmPasswordField.getText().toString();
    }

    private boolean checkForUserInDatabase(){
        mUser = miSpendDAO.getUserByUsername(mUsername);
        if(mUser == null){
            Toast.makeText(this, "no user " + mUsername + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getDatabase() {
        miSpendDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getiSpendDAO();
    }
}
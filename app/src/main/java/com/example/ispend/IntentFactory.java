package com.example.ispend;

import android.content.Context;
import android.content.Intent;

public class IntentFactory {

    // Private constructor to prevent instantiation
    private IntentFactory() {}

    // Method to create an Intent for MainActivity
    public static Intent newMainActivityIntent(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.getUserIdKey(), userId);

        return intent;
    }

    // Method to create an Intent for Login activity
    public static Intent newLoginActivityIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);

        return intent;
    }

    public static Intent newSignUpActivityIntent(Context applicationContext) {
        Intent intent = new Intent(applicationContext, SignUpActivity.class);

        return intent;
    }

    // Add more methods for other Intents as needed
}

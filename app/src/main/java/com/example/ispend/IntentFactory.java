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

    public static Intent newSignUpActivityIntent(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);

        return intent;
    }

    public static Intent newProductActivityIntent(Context context, int userId) {
        Intent intent = new Intent(context, ProductActivity.class);
        userId = intent.getIntExtra(MainActivity.getUserIdKey(), userId);
        intent.putExtra(MainActivity.getUserIdKey(), userId);

        return intent;
    }

    public static Intent newAddProductActivity(Context context, int userId) {
        Intent intent = new Intent(context, AddProductActivity.class);
        userId = intent.getIntExtra(MainActivity.getUserIdKey(), userId);
        intent.putExtra(MainActivity.getUserIdKey(), userId);

        return intent;
    }

    public static Intent newDeleteProductActivity(Context context, int userId) {
        Intent intent = new Intent(context, DeleteProductActivity.class);
        userId = intent.getIntExtra(MainActivity.getUserIdKey(), userId);
        intent.putExtra(MainActivity.getUserIdKey(), userId);


        return intent;
    }

    public static Intent newAddToCartActivity(Context context, int userId) {
        Intent intent = new Intent(context, AddToCartActivity.class);
        userId = intent.getIntExtra(MainActivity.getUserIdKey(), userId);
        intent.putExtra(MainActivity.getUserIdKey(), userId);

        return intent;
    }

    public static Intent newRemoveFromCartActivity(Context context, int userId) {
        Intent intent = new Intent(context, RemoveFromCartActivity.class);
        userId = intent.getIntExtra(MainActivity.getUserIdKey(), userId);
        intent.putExtra(MainActivity.getUserIdKey(), userId);

        return intent;
    }

    // Add more methods for other Intents as needed
}

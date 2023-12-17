package com.example.ispend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import com.example.ispend.DB.AppDatabase;
import com.example.ispend.DB.iSpendDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.ispend.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.ispend.PREFERENCES_KEY";

    private TextView mWelcomeTextView;
    private Button mOrdersButton;
    private Button mCartButton;
    private Button mProductsButton;
    private Button mSignOffButton;
    private Button mCartsButton;
    private Button mUsersButton;
    private ConstraintLayout.LayoutParams layoutParams;
    private iSpendDAO miSpendDAO;

    private SharedPreferences mPreferences = null;
    private int mUserId = -1;
    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();

        checkForUser();

        loginUser(mUserId);

        wireupDisplay();


    }

    private void wireupDisplay() {
        mWelcomeTextView = findViewById(R.id.welcome_textview);
        mOrdersButton = findViewById(R.id.orders_button);
        mCartButton = !mUser.isIsAdmin() ? findViewById(R.id.cart_button) : null;
        mProductsButton = findViewById(R.id.products_button);
        mSignOffButton = findViewById(R.id.sign_off_button);
        mCartsButton = mUser.isIsAdmin() ? findViewById(R.id.cart_button) : null;
        mUsersButton = mUser.isIsAdmin() ? findViewById(R.id.users_button) : null;

        mWelcomeTextView.setText("Welcome " + mUser.getUsername());

        if (!mUser.isIsAdmin()) {
            layoutParams = (ConstraintLayout.LayoutParams) mOrdersButton.getLayoutParams();
            layoutParams.topToBottom = mWelcomeTextView.getId();
            mOrdersButton.setVisibility(Button.VISIBLE);
            /*mOrdersButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = IntentFactory.newOrdersActivityIntent(MainActivity.this, mUserId);
                    startActivity(intent);
                }
            } );*/

            layoutParams = (ConstraintLayout.LayoutParams) mCartButton.getLayoutParams();
            layoutParams.topToBottom = mOrdersButton.getId();
            mCartButton.setVisibility(Button.VISIBLE);
            /*mCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = IntentFactory.newCartActivityIntent(MainActivity.this, mUserId  );
                    startActivity(intent);
                }
            } );*/

            layoutParams = (ConstraintLayout.LayoutParams) mProductsButton.getLayoutParams();
            layoutParams.topToBottom = mCartButton.getId();
            mProductsButton.setVisibility(Button.VISIBLE);
            mProductsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = IntentFactory.newProductActivityIntent(MainActivity.this , mUser.getUserId());
                    startActivity(intent);
                }
            } );

            layoutParams = (ConstraintLayout.LayoutParams) mSignOffButton.getLayoutParams();
            layoutParams.topToBottom = mProductsButton.getId();
            mSignOffButton.setVisibility(Button.VISIBLE);
            mSignOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutUser();
                }
            });
        } else {
            layoutParams = (ConstraintLayout.LayoutParams) mOrdersButton.getLayoutParams();
            layoutParams.topToBottom = mWelcomeTextView.getId();
            mOrdersButton.setVisibility(Button.VISIBLE);
            /*mOrdersButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = IntentFactory.newOrdersActivityIntent(MainActivity.this, mUserId);
                    startActivity(intent);
                }
            } );*/

            layoutParams = (ConstraintLayout.LayoutParams) mCartsButton.getLayoutParams();
            layoutParams.topToBottom = mOrdersButton.getId();
            mCartsButton.setText(mCartsButton.getText() + "s");
            mCartsButton.setVisibility(Button.VISIBLE);
            /*mCartsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = IntentFactory.newCartActivityIntent(MainActivity.this, mUserId);
                    startActivity(intent);
                }
            } );*/

            layoutParams = (ConstraintLayout.LayoutParams) mProductsButton.getLayoutParams();
            layoutParams.topToBottom = mCartsButton.getId();
            mProductsButton.setVisibility(Button.VISIBLE);
            mProductsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = IntentFactory.newProductActivityIntent(MainActivity.this, mUser.getUserId());
                    startActivity(intent);
                }
            } );

            layoutParams = (ConstraintLayout.LayoutParams) mUsersButton.getLayoutParams();
            layoutParams.topToBottom = mProductsButton.getId();
            mUsersButton.setVisibility(Button.VISIBLE);
            /*mUsersButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = IntentFactory.newUsersActivityIntent(MainActivity.this, mUserId);
                    startActivity(intent);
                }
            } );*/

            layoutParams = (ConstraintLayout.LayoutParams) mSignOffButton.getLayoutParams();
            layoutParams.topToBottom = mUsersButton.getId();
            mSignOffButton.setVisibility(Button.VISIBLE);
            mSignOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutUser();
                }
            });
        }
    }

    private void loginUser(int userId) {
        mUser = miSpendDAO.getUserByUserId(userId);
        addUserToPreference(userId);
        invalidateOptionsMenu();
    }



    private void addUserToPreference(int userId) {
        if (mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void getDatabase() {
        miSpendDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getiSpendDAO();
    }

    private void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        //do we have a user in the preferences?
        if (mUserId != -1) {
            return;
        }


        if (mPreferences == null) {
            getPrefs();
        }

        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if (mUserId != -1) {
            return;
        }

        //do we have any users at all?
        List<User> users = miSpendDAO.getAllUsers();
        if (users.size() <= 0) {
            User defaultAdminUser = new User("admin2", "dac123", true);
            User defaultUser = new User("testuser1", "dac123", false);
            miSpendDAO.insert(defaultAdminUser, defaultUser);
        }

        Intent intent = IntentFactory.newLoginActivityIntent(this);
        startActivity(intent);

    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void logoutUser() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearUserFromIntent();
                        clearUserFromPref();
                        mUserId = -1;
                        checkForUser();
                    }
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //We don't really need to do anything here.

                    }
                });

        alertBuilder.create().show();

    }

    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref() {
        addUserToPreference(-1);
    }



    public static String getUserIdKey() {
        return USER_ID_KEY;
    }


}
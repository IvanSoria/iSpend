package com.example.ispend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ispend.DB.AppDatabase;
import com.example.ispend.DB.iSpendDAO;

public class AddProductActivity extends AppCompatActivity {
    private EditText mProductNameField;
    private EditText mProductDescriptionField;
    private EditText mProductPriceField;
    private EditText mProductQuantityField;
    private Button mAddProductButton;
    private Button mBackButton;
    private iSpendDAO miSpendDAO;
    private String mProductName;
    private int mUserId = -1;
    private User mUser;

    private String mProductDescription;
    private int mProductQuantity;
    private double mProductPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        getDatabase();

        wireUpDisplay();
    }

    private void wireUpDisplay() {
        mProductNameField = findViewById(R.id.product_name_edittext);
        mProductDescriptionField = findViewById(R.id.product_description_edittext);
        mProductPriceField = findViewById(R.id.product_price_edittext);
        mProductQuantityField = findViewById(R.id.product_quantity_edittext);

        mAddProductButton = findViewById(R.id.add_product_button);
        mBackButton = findViewById(R.id.back_button);

        mUserId = getIntent().getIntExtra(MainActivity.getUserIdKey(), -1);
        mUser = miSpendDAO.getUserByUserId(mUserId);

        mAddProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(checkForProductInDatabase()) {
                    Toast.makeText(AddProductActivity.this, "Product already exists", Toast.LENGTH_SHORT).show();
                }
                else {
                    addProductToDatabase();
                    Intent intent = IntentFactory.newProductActivityIntent(AddProductActivity.this, getIntent().getIntExtra(MainActivity.getUserIdKey(), -1));
                    startActivity(intent);
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = IntentFactory.newProductActivityIntent(AddProductActivity.this, getIntent().getIntExtra(MainActivity.getUserIdKey(), -1));
                startActivity(intent);
            }
        });
    }

    private void addProductToDatabase() {
        Product product = new Product(mProductName, mProductDescription, mProductPrice, mProductQuantity);
        miSpendDAO.insert(product);
    }

    private boolean checkForProductInDatabase() {
        return miSpendDAO.getProductByName(mProductName) != null;
    }

    private void getValuesFromDisplay() {
        mProductName = mProductNameField.getText().toString();
        mProductDescription = mProductDescriptionField.getText().toString();
        try{
        mProductPrice = Double.parseDouble(mProductPriceField.getText().toString());

        mProductQuantity = Integer.parseInt(mProductQuantityField.getText().toString());
        }
        catch (Exception e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            //Set default values for price and quantity if invalid input
            mProductPriceField.setText("0");
            mProductQuantityField.setText("0");
            mProductPrice = 1.00;
            mProductQuantity = 1;
        }
    }

    private void getDatabase() {
        miSpendDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getiSpendDAO();
    }
}
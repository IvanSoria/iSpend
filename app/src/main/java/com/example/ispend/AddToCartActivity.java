package com.example.ispend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ispend.DB.AppDatabase;
import com.example.ispend.DB.iSpendDAO;

import java.util.Date;

public class AddToCartActivity extends AppCompatActivity {
    private int mUserId = -1;
    private int mProductId = -1;
    private int mQuantity = -1;
    private User mUser;
    private Product mProduct;
    private EditText mProductNameField;
    private EditText mProductQuantityField;
    private String mProductName;
    private int mProductQuantity;
    private Button mAddToCartButton;
    private Button mMainMenuButton;
    private iSpendDAO miSpendDAO;
    private ShoppingCart mShoppingCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        getDatabase();

        setupExtras();


        checkForShoppingCart();

        wireUpDisplay();
    }

    private void setupExtras() {
        mUserId = getIntent().getIntExtra(MainActivity.getUserIdKey(), -1);
        mUser = miSpendDAO.getUserByUserId(mUserId);

        mProductId = getIntent().getIntExtra(ProductActivity.getProductIdKey(), -1);
        mProduct = miSpendDAO.getProductByProductId(mProductId);

        mQuantity = getIntent().getIntExtra(ProductActivity.getProductQuantityKey(), -1);

    }

    private void checkForShoppingCart() {
        if(miSpendDAO.getShoppingCartByUserId(mUserId) == null) {
            mShoppingCart = new ShoppingCart(new Date(), mUserId, mProductId, mQuantity );
            miSpendDAO.insert(mShoppingCart);
        }
        else {
            mShoppingCart = miSpendDAO.getShoppingCartByUserId(mUserId);
        }

    }

    private void wireUpDisplay() {
        mProductNameField = findViewById(R.id.product_name_add_to_cart_edittext);
        mProductQuantityField = findViewById(R.id.desired_quantity_edittext);

        mAddToCartButton = findViewById(R.id.add_to_cart_button);
        mMainMenuButton = findViewById(R.id.main_menu_button);

        mUserId = getIntent().getIntExtra(MainActivity.getUserIdKey(), -1);
        mUser = miSpendDAO.getUserByUserId(mUserId);

        mAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(!checkForProductInDatabase()) {
                    Toast.makeText(AddToCartActivity.this, "Product does not exist", Toast.LENGTH_SHORT).show();}
                else {
                    Product product = miSpendDAO.getProductByName(mProductName);
                    if(product.getProductQuantity() < mProductQuantity) {
                        Toast.makeText(AddToCartActivity.this, "Not enough product to add", Toast.LENGTH_SHORT).show();
                        Toast.makeText(AddToCartActivity.this, "Adding all product to cart", Toast.LENGTH_SHORT).show();
                        mShoppingCart.setQuantity(product.getProductQuantity());
                    }
                    else {
                        product.setProductQuantity(product.getProductQuantity() - mProductQuantity);
                        if(product.getProductQuantity() == 0) {
                            miSpendDAO.delete(product);
                        }
                        else {
                            miSpendDAO.update(product);
                            Toast.makeText(AddToCartActivity.this, "Product amount updated", Toast.LENGTH_SHORT).show();
                            mShoppingCart.setProductId(product.getProductId());
                            mShoppingCart.setQuantity(mProductQuantity);
                            miSpendDAO.update(mShoppingCart);
                        }
                    }

                    Intent intent = IntentFactory.newProductActivityIntent(AddToCartActivity.this,  getIntent().getIntExtra(MainActivity.getUserIdKey(), -1) );
                    startActivity(intent);
                }
            }
        } );

        mMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = IntentFactory.newMainActivityIntent(AddToCartActivity.this, getIntent().getIntExtra(MainActivity.getUserIdKey(), -1));
                startActivity(intent);
            }
        });
    }

    private void getValuesFromDisplay() {
        mProductName = mProductNameField.getText().toString();
        try {
            mProductQuantity = Integer.parseInt(mProductQuantityField.getText().toString());
        }
        catch (Exception e) {
            Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Default quantity 1", Toast.LENGTH_SHORT).show();
            mProductQuantity = 1;
        }

    }

    private boolean checkForProductInDatabase() {
        Product product = miSpendDAO.getProductByName(mProductName);
        if(product == null) {
            return false;
        }
        else {
            return true;
        }
    }



    private void getDatabase() {
        miSpendDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getiSpendDAO();
    }
}
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

public class DeleteProductActivity extends AppCompatActivity {

    private EditText mProductNameField;
    private EditText RemoveQuantityField;
    private String mProductName;
    private int mProductQuantity;
    private Button mDeleteProductButton;
    private Button mBackButton;
    private int mUserId = -1;
    private User mUser;


    private iSpendDAO miSpendDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);

        getDatabase();

        wireUpDisplay();

    }

    private void wireUpDisplay() {
        mProductNameField = findViewById(R.id.product_name_edittext);
        RemoveQuantityField = findViewById(R.id.desired_quantity_edittext);

        mDeleteProductButton = findViewById(R.id.delete_product_button);
        mBackButton = findViewById(R.id.back_button);

        mUserId = getIntent().getIntExtra(MainActivity.getUserIdKey(), -1);
        mUser = miSpendDAO.getUserByUserId(mUserId);

        mDeleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(!checkForProductInDatabase()) {
                    Toast.makeText(DeleteProductActivity.this, "Product does not exist", Toast.LENGTH_SHORT).show();}
                else {
                    Product product = miSpendDAO.getProductByName(mProductName);
                    if(product.getProductQuantity() < mProductQuantity) {
                        Toast.makeText(DeleteProductActivity.this, "Not enough product to delete", Toast.LENGTH_SHORT).show();
                        Toast.makeText(DeleteProductActivity.this, "Deleting product from inventory", Toast.LENGTH_SHORT).show();
                        miSpendDAO.delete(product);
                    }
                    else {
                        product.setProductQuantity(product.getProductQuantity() - mProductQuantity);
                        miSpendDAO.update(product);
                        Toast.makeText(DeleteProductActivity.this, "Product amount updated", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = IntentFactory.newProductActivityIntent(DeleteProductActivity.this,  getIntent().getIntExtra(MainActivity.getUserIdKey(), -1) );
                    startActivity(intent);
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = IntentFactory.newProductActivityIntent(DeleteProductActivity.this, getIntent().getIntExtra(MainActivity.getUserIdKey(), -1));
                startActivity(intent);
            }
        });
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

    private void getValuesFromDisplay() {
        mProductName = mProductNameField.getText().toString();
        try {
            mProductQuantity = Integer.parseInt(RemoveQuantityField.getText().toString());
        }
        catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
            //default to 1 if no quantity is entered
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
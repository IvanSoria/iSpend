package com.example.ispend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ispend.DB.AppDatabase;
import com.example.ispend.DB.iSpendDAO;

import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private static final String PRODUCT_ID_KEY = "com.example.ispend.productIdKey";
    private static final String PRODUCT_QUANTITY_KEY = "com.example.ispend.productQuantityKey";

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private iSpendDAO miSpendDAO;

    private Button mAddProductButton;
    private int mUserId = -1;
    private User mUser;

    private Button mMainMenuButton;
    private Button mDeleteProductButton;
    private Button mAddToCartButton;
    private Button mRemoveFromCartButton;
    private ConstraintLayout.LayoutParams layoutParams;

    public static String getProductIdKey() {
        return PRODUCT_ID_KEY;
    }

    public static String getProductQuantityKey() {
        return PRODUCT_QUANTITY_KEY;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getDatabase();

        setupRecycler();

        wireupDisplay();

    }

    private void wireupDisplay() {
        mAddProductButton = findViewById(R.id.add_product_button);
        mMainMenuButton = findViewById(R.id.main_menu_button);
        mDeleteProductButton = findViewById(R.id.delete_product_button);
        mAddToCartButton = findViewById(R.id.add_to_cart_button);
        mRemoveFromCartButton = findViewById(R.id.remove_from_cart_button);


        mUserId = getIntent().getIntExtra(MainActivity.getUserIdKey(), -1);
        mUser = miSpendDAO.getUserByUserId(mUserId);
        if (!mUser.isIsAdmin())
        {
            mAddProductButton.setVisibility(View.GONE);
            mDeleteProductButton.setVisibility(View.GONE);


            layoutParams = (ConstraintLayout.LayoutParams) mAddToCartButton.getLayoutParams();
            layoutParams.topToBottom = R.id.products_recyclerview;
            mAddToCartButton.setVisibility(View.VISIBLE);

            layoutParams = (ConstraintLayout.LayoutParams) mRemoveFromCartButton.getLayoutParams();
            layoutParams.topToBottom = R.id.add_to_cart_button;
            mRemoveFromCartButton.setVisibility(View.VISIBLE);

            layoutParams = (ConstraintLayout.LayoutParams) mMainMenuButton.getLayoutParams();
            layoutParams.topToBottom = R.id.remove_from_cart_button;
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            mMainMenuButton.setVisibility(View.VISIBLE);


            mAddToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //open add product activity
                    Intent intent = IntentFactory.newAddToCartActivity(
                            ProductActivity.this, getIntent().getIntExtra(MainActivity.getUserIdKey(), -1 ),
                            getIntent().getIntExtra(ProductActivity.getProductIdKey(), -1),
                            getIntent().getIntExtra(ProductActivity.getProductQuantityKey(), -1));
                    startActivity(intent);
                }
            });

            mRemoveFromCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //open add product activity
                    Intent intent = IntentFactory.newRemoveFromCartActivity(ProductActivity.this, getIntent().getIntExtra(MainActivity.getUserIdKey(), -1));
                    startActivity(intent);
                }
            });


        }
        else {

            mAddToCartButton.setVisibility(View.GONE);
            mRemoveFromCartButton.setVisibility(View.GONE);

            layoutParams = (ConstraintLayout.LayoutParams) mAddProductButton.getLayoutParams();
            layoutParams.topToBottom = R.id.products_recyclerview;
            mAddProductButton.setVisibility(View.VISIBLE);
            layoutParams = (ConstraintLayout.LayoutParams) mDeleteProductButton.getLayoutParams();
            layoutParams.topToBottom = R.id.add_product_button;
            mDeleteProductButton.setVisibility(View.VISIBLE);

            layoutParams = (ConstraintLayout.LayoutParams) mMainMenuButton.getLayoutParams();
            layoutParams.topToBottom = R.id.delete_product_button;
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            mMainMenuButton.setVisibility(View.VISIBLE);

            mAddProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //open add product activity
                    Intent intent = IntentFactory.newAddProductActivity(ProductActivity.this, getIntent().getIntExtra(MainActivity.getUserIdKey(), -1));
                    startActivity(intent);
                }
            });

            //wire up the delete product button
            mDeleteProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //open delete product activity
                    Intent intent = IntentFactory.newDeleteProductActivity(ProductActivity.this, getIntent().getIntExtra(MainActivity.getUserIdKey(), -1));
                    startActivity(intent);
                }
            });
        }



        mMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open main menu activity
                Intent intent = IntentFactory.newMainActivityIntent(ProductActivity.this, getIntent().getIntExtra("userId", -1));
                startActivity(intent);
            }
        });
    }

    private void getDatabase() {
        miSpendDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getiSpendDAO();
    }

    private void setupRecycler() {
        recyclerView = findViewById(R.id.products_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getProducts();
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);
    }



    private void getProducts() {
        productList = (miSpendDAO.getAllProducts()).size() == 0 ? null : miSpendDAO.getAllProducts();
    }
}
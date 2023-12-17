package com.example.ispend;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.ispend.DB.AppDatabase;

@Entity(tableName = AppDatabase.PRODUCT_TABLE)
public class Product {
    @PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "productId")
    private int mProductId;
    private String mProductName;
    private String mProductDescription;
    private double mProductPrice;
    private int mProductQuantity;

    public Product( String mProductName, String mProductDescription, double mProductPrice, int mProductQuantity) {

        this.mProductName = mProductName;
        this.mProductDescription = mProductDescription;
        this.mProductPrice = mProductPrice;
        this.mProductQuantity = mProductQuantity;

    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String getProductDescription() {
        return mProductDescription;
    }

    public void setProductDescription(String productDescription) {
        mProductDescription = productDescription;
    }

    public double getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(double productPrice) {
        mProductPrice = productPrice;
    }

    public int getProductQuantity() {
        return mProductQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        mProductQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "mProductId=" + mProductId +
                ", mProductName='" + mProductName + '\'' +
                ", mProductDescription='" + mProductDescription + '\'' +
                ", mProductPrice=" + mProductPrice +
                '}';
    }
}

package com.example.ispend;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.ispend.DB.AppDatabase;

import java.util.Date;


@Entity(tableName = AppDatabase.SHOPPING_CART_TABLE,
        foreignKeys = {
        @ForeignKey(entity = Product.class,
                parentColumns = "productId",
                childColumns = "productId",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = User.class,
                parentColumns = "userId",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE)
        })
public class ShoppingCart {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cartId")
    private int mCartId;
    @ColumnInfo(name = "productId")
    private int mProductId;
    @ColumnInfo(name = "userId")
    private int mUserId;
    private Date mEffectiveDate;

    public ShoppingCart(Date mEffectiveDate) {
        this.mEffectiveDate = mEffectiveDate;

    }

    public int getCartId() {
        return mCartId;
    }

    public void setCartId(int cartId) {
        mCartId = cartId;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public Date getEffectiveDate() {
        return mEffectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        mEffectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "mCartId=" + mCartId +
                ", mProductId=" + mProductId +
                ", mUserId=" + mUserId +
                ", mEffectiveDate=" + mEffectiveDate +
                '}';
    }
}

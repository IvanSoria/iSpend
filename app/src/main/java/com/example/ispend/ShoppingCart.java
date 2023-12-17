package com.example.ispend;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.ispend.DB.AppDatabase;

import java.util.Date;


@Entity(tableName = AppDatabase.SHOPPING_CART_TABLE/*,
        foreignKeys = {
        @ForeignKey(entity = Product.class,
                parentColumns = "productId",
                childColumns = "productId",
                onDelete = ForeignKey.NO_ACTION),
        @ForeignKey(entity = User.class,
                parentColumns = "userId",
                childColumns = "userId",
                onDelete = ForeignKey.NO_ACTION)
        }*/)
public class ShoppingCart {
    @PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "cartId")
    private int mCartId;
    //@ColumnInfo(name = "productId")
    private int mProductId;
    //@ColumnInfo(name = "userId")
    private int mUserId;
    private Date mEffectiveDate;
    private int mQuantity;

    private double mTotalPrice;

    public ShoppingCart(Date mEffectiveDate, int mProductId, int mUserId,
                        int mQuantity) {
        this.mEffectiveDate = mEffectiveDate;
        this.mProductId = mProductId;
        this.mUserId = mUserId;
        this.mQuantity = mQuantity;

    }

    public double getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        mTotalPrice = totalPrice;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
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

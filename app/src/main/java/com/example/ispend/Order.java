package com.example.ispend;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.ispend.DB.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.ORDER_TABLE,
    foreignKeys = {
                @ForeignKey(entity = ShoppingCart.class,
                        parentColumns = "cartId",
                        childColumns = "cartId",
                        onDelete = ForeignKey.CASCADE)
        })
public class Order {

      @PrimaryKey(autoGenerate = true)
      private int mOrderId;
      @ColumnInfo(name = "cartId")
      private int mCartId;
      private Date mEffectiveDate;

      public Order(Date effectiveDate) {
          this.mEffectiveDate = effectiveDate;
      }

      public int getOrderId() {
          return mOrderId;
      }

      public void setOrderId(int mOrderId) {
          this.mOrderId = mOrderId;
      }

    public int getCartId() {
        return mCartId;
    }

    public void setCartId(int cartId) {
        mCartId = cartId;
    }

    public Date getEffectiveDate() {
        return mEffectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        mEffectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "mOrderId=" + mOrderId +
                ", mCartId=" + mCartId +
                ", mEffectiveDate=" + mEffectiveDate +
                '}';
    }
}

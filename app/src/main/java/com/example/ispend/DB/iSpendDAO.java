package com.example.ispend.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ispend.Product;
import com.example.ispend.ShoppingCart;
import com.example.ispend.User;

import java.util.List;

@Dao
public interface iSpendDAO {

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserName = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserId = :userId")
    User getUserByUserId(int userId);

    @Insert
    void insert(Product... products);

    @Update
    void update(Product... products);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE)
    List<Product> getAllProducts();

    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE + " WHERE mProductName = :productName")
    Product getProductByName(String productName);

    @Insert
    void insert(ShoppingCart... shoppingCarts);

    @Update
    void update(ShoppingCart... shoppingCarts);

    @Delete
    void delete(ShoppingCart shoppingCart);

    @Query("SELECT * FROM " + AppDatabase.SHOPPING_CART_TABLE)
    List<ShoppingCart> getAllShoppingCarts();

    @Query("SELECT * FROM " + AppDatabase.SHOPPING_CART_TABLE + " WHERE mUserId = :userId")
    ShoppingCart getShoppingCartByUserId(int userId);

    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE + " WHERE mProductId = :productId")
    Product getProductByProductId(int productId);
}


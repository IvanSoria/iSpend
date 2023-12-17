package com.example.ispend;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.ispend.DB.AppDatabase;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "userId")
    private int mUserId;
    private String mUsername;
    private String mPassword;
    private boolean mIsAdmin;

    public User(String mUsername, String mPassword, boolean mIsAdmin) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mIsAdmin = mIsAdmin;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public boolean isIsAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(boolean mIsAdmin) {
        this.mIsAdmin = mIsAdmin;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUserId=" + mUserId +
                ", mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mIsAdmin=" + mIsAdmin +
                '}';
    }
}

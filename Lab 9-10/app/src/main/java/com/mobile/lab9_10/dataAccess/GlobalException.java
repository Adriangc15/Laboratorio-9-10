package com.mobile.lab9_10.dataAccess;


import android.util.Log;

public class GlobalException extends Exception {
    public GlobalException(){
        super();
    }

    public GlobalException(String msg){
        super(msg);
        Log.e("SQLException", msg);
    }
}

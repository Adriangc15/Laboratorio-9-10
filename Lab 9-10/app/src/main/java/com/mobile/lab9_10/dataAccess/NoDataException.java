package com.mobile.lab9_10.dataAccess;

public class NoDataException extends Exception {
    public NoDataException(){
        super();
    }

    public NoDataException(String msg){
        super(msg);
    }
}

package com.mobile.lab9_10.dataAccess;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Service {
    private static final String DB_PATH = "%s/lab9-10";
    protected SQLiteDatabase connection = null;
    private String contextPath;

    protected Service(String contextPath){
        this.contextPath = contextPath;
    };

    protected void connect() throws SQLException {
        this.connection = SQLiteDatabase.openDatabase(String.format(DB_PATH, this.contextPath), null, SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    protected void disconnect() throws SQLException{
        if (this.connection != null && this.connection.isOpen()){
            this.connection.close();
        }
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}

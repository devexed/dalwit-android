package com.devexed.dbsourceandroid;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteStatement;

/**
 * Accessor to bind values to and retrieve values from Android SQLite interfaces.
 */
public interface AndroidSQLiteAccessor {

    void set(SQLiteBindable bindable, int index, Object value);

    Object get(Cursor cursor, int index) throws SQLException;

}

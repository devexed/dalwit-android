package com.devexed.dbsourceandroid;

/**
 * Wrapper around bindable Android types required as {@link android.database.sqlite.SQLiteStatement} and
 * {@link android.database.sqlite.SQLiteQuery} implement the same bind methods but don't share a common interface.
 */
interface SQLiteBindable {

    void bindNull(int index);

    void bindLong(int index, long value);

    void bindString(int index, String value);

    void bindDouble(int index, double value);

    void bindBlob(int index, byte[] bytes);

}

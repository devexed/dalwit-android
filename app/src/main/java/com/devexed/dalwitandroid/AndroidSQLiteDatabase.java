package com.devexed.dalwitandroid;

import org.sqlite.database.SQLException;
import org.sqlite.database.sqlite.SQLiteDatabase;
import com.devexed.dalwit.AccessorFactory;
import com.devexed.dalwit.ColumnNameMapper;
import com.devexed.dalwit.DatabaseException;
import com.devexed.dalwit.Transaction;

public final class AndroidSQLiteDatabase extends AndroidSQLiteAbstractDatabase {

    public AndroidSQLiteDatabase(SQLiteDatabase connection,
                                 AccessorFactory<AndroidSQLiteBindable, android.database.Cursor, SQLException> accessorFactory,
                                 ColumnNameMapper columnNameMapper) {
        super(connection, accessorFactory, columnNameMapper);
    }

    @Override
    public Transaction transact() {
        checkActive();
        return openChildTransaction(new AndroidSQLiteRootTransaction(this));
    }

    @Override
    void closeResource() {
        // Sanity check. Some Android versions will silently keep the SQLite database locked if a transaction is open
        // when closing the connection.
        if (connection.inTransaction()) throw new DatabaseException("Cannot close while child transaction is open");

        try {
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

}

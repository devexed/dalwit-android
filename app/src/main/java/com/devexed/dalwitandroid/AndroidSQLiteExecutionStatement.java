package com.devexed.dalwitandroid;

import android.database.SQLException;

import com.devexed.dalwit.DatabaseException;
import com.devexed.dalwit.ExecutionStatement;
import com.devexed.dalwit.Query;
import com.devexed.dalwit.Transaction;

final class AndroidSQLiteExecutionStatement extends AndroidSQLiteStatementStatement implements ExecutionStatement {

    public AndroidSQLiteExecutionStatement(AndroidSQLiteAbstractDatabase database, Query query) {
        super(database, query);
    }

    @Override
    public void execute(Transaction transaction) {
        checkNotClosed();
        checkActiveTransaction(transaction);

        try {
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

}
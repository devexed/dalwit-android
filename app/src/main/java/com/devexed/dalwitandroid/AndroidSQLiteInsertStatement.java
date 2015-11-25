package com.devexed.dalwitandroid;

import android.database.SQLException;
import com.devexed.dalwit.Cursor;
import com.devexed.dalwit.DatabaseException;
import com.devexed.dalwit.InsertStatement;
import com.devexed.dalwit.Query;
import com.devexed.dalwit.util.Cursors;

import java.util.Map;

final class AndroidSQLiteInsertStatement extends AndroidSQLiteStatementStatement implements InsertStatement {

    private final String key;

    public AndroidSQLiteInsertStatement(AndroidSQLiteAbstractDatabase database, Query query, Map<String, Class<?>> keys) {
        super(database, query, keys);

        if (keys.size() > 1)
            throw new DatabaseException("Only a single generated key column is supported.");

        key = keys.keySet().iterator().next();
        Class<?> type = keys.get(key);

        if (type != Long.TYPE) throw new DatabaseException("Generated key column must be of type Long.TYPE");
    }

    @Override
    public Cursor insert() {
        checkNotClosed();

        try {
            // Select last inserted id as key.
            final long generatedKey = statement.executeInsert();

            if (generatedKey < 0) return Cursors.empty();

            return Cursors.singleton(new Cursors.ColumnFunction() {
                @Override
                @SuppressWarnings("unchecked")
                public <E> E get(String column) {
                    if (!key.equals(column)) throw new DatabaseException("Column must be key column " + key);

                    return (E) (Long) generatedKey;
                }
            });
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

}

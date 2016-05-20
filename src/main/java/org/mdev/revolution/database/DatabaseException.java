package org.mdev.revolution.database;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class DatabaseException extends SQLException {
    public DatabaseException() {
        super();
    }

    public DatabaseException(String msg) {
        super(msg);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public DatabaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

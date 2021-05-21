package davenkin.step3_connection_holder;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    public TransactionManager() {
    }

    public final void start() throws SQLException {
        System.out.println(">>>>>>>>>>start transaction...");
        Connection connection = CurrentThreadConnectionHolder.getConnection();
        connection.setAutoCommit(false);
    }

    public final void commit() throws SQLException {
        System.out.println(">>>>>>>>>>>>>>>start commit...");
        Connection connection = CurrentThreadConnectionHolder.getConnection();
        connection.commit();
    }

    public final void rollback() {
        System.out.println(">>>>>>>>>>>>>>start rollback");
        Connection connection = null;
        try {
            connection = CurrentThreadConnectionHolder.getConnection();
            connection.rollback();

        } catch (SQLException e) {
            throw new RuntimeException("Couldn't rollback on connection[" + connection + "].", e);
        }
    }

    public final void close() {
        System.out.println(">>>>>>>>>>>>>start close...");
        Connection connection = null;
        try {
            connection = CurrentThreadConnectionHolder.getConnection();
            connection.setAutoCommit(true);
            connection.setReadOnly(false);
            connection.close();
            CurrentThreadConnectionHolder.removeConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't close connection[" + connection + "].", e);
        }
    }

}

package davenkin.step3_connection_holder;

import davenkin.DataSourceFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class CurrentThreadConnectionHolder {
    private static ThreadLocal<Connection> localConnectionHolder = ThreadLocal.withInitial(() -> {
        System.out.println(">>>>>>>>>>>>> 获取数据库连接");
        try {
            return DataSourceFactory.createDataSource().getConnection();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    });

    public static Connection getConnection() throws SQLException {
        Connection existing = localConnectionHolder.get();
        if (existing == null) {
            System.out.println(">>>>>>>>>>>>>>>*******");
            Connection connection = DataSourceFactory.createDataSource().getConnection();
            localConnectionHolder.set(connection);
            return connection;
        }
        System.out.println(">>>>>>>>>>>>return cached");
        return existing;
    }

    public static void removeConnection() {
        System.out.println(">>>>>>>>>>>>>>>>>>>removed");
        localConnectionHolder.remove();
    }

}

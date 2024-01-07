import Utils.Log;
import com.sun.jdi.ArrayReference;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlDatabase {

    public static final String MYSQL_JDBCDRIVER = "com.mysql.jdbc.Driver";
    //TODO URL設定
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/mydatabase";
    public static final String MYSQL_USER = "root";
    public static final String MYSQL_PASS = "";
    public static final String TABEL_DOLL = "doll";

    private Connection connection;
    private Statement statement;

    private ResultSet rs;

    public boolean connectDatabase() {

        boolean isCennected = false;

        try {

            Class.forName(MYSQL_JDBCDRIVER);
            Log.i("MysqlDatabase", "connectDatabase", "mysql とつながっています");

            connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASS);

            Log.i("MysqlDatabase", "connectDatabase", "mysql とつながりました");\
            statement = connection.createStatement();
            isCennected = true;

        } catch (ClassNotFoundException e) {

            Log.i("MysqlDatabase", "connectDatabase", "DriverManager 異常");
            e.printStackTrace();

        } catch (SQLException e) {

            Log.i("MysqlDatabase", "connectDatabase", "cennceted error");
            e.printStackTrace();

        } finally {
            close();
        }

        return isCennected;

    }


    /**
     * ぬいぐるみデータ検索
     * @param tableName
     * @param dataName
     * @param where
     * @return
     */
    public List<String> selectDoll(String tableName, String dataName, String where) {

        try {

            if(statement == null) {

                Log.i("MysqlDatabase", "select", "statement is null object");
                return null;

            }else {

                String sqlCommand;

                if(where != null) {

                    sqlCommand = "SELECT " + dataName + " FROM " + tableName + " WHERE " + where;

                }else {

                    sqlCommand = "SELECT " + dataName + " FROM " + tableName;

                }
                rs = statement.executeQuery(sqlCommand);

                ArrayList datalist = new ArrayList();

                while (rs.next()) {

                    //TODO データ処理

                }


            }

        } catch (SQLException e) {

            Log.i("MysqlDatabase", "select", "データ検索に失敗しました");
            e.printStackTrace();

        }finally {
            close();
        }

    }

    public void close() {

        try {

            if(rs != null) {

                rs.close();

            }

            if(statement != null) {

                statement.close();

            }

            if(connection != null) {

                connection.close();

            }

        } catch (SQLException e) {

            Log.i("MysqlDatabase", "close", "close error");
            e.printStackTrace();

        }

    }


}

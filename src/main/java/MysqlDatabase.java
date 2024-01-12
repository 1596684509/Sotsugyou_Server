import Utils.Log;
import obj.Doll;
import obj.User;

import java.sql.*;

public class MysqlDatabase {

    public static final String MYSQL_JDBCDRIVER = "com.mysql.cj.jdbc.Driver";
    //TODO URL設定
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/sotsugyou";
    public static final String MYSQL_USER = "root";
    public static final String MYSQL_PASS = "";
    public static final String TABLE_NAME_DOLL = "doll";
    public static final String TABLE_NAME_USER = "user";

    private Connection connection;
    private Statement statement;

    private ResultSet rs;

    public boolean connectDatabase() {

        boolean isCennected = false;

        try {

            Class.forName(MYSQL_JDBCDRIVER);
            Log.i("MysqlDatabase", "connectDatabase", "mysql とつながっています");

            connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASS);

            Log.i("MysqlDatabase", "connectDatabase", "mysql とつながりました");
            statement = connection.createStatement();
            isCennected = true;

        } catch (ClassNotFoundException e) {

            Log.i("MysqlDatabase", "connectDatabase", "DriverManager 異常");
            e.printStackTrace();
            close();

        } catch (SQLException e) {

            Log.i("MysqlDatabase", "connectDatabase", "cennceted error");
            e.printStackTrace();
            close();

        }

        return isCennected;

    }

    public boolean insertUser(User user) {

        boolean isInsertend = false;

        String sqlCommand = "INSERT INTO " + TABLE_NAME_USER + " (userid, password, iconid, name) VALUES(?, ?, ?, ?)" +
                "ON DUPLICATE KEY UPDATE userid = userid, password = VALUES(password), iconid = VALUES(iconid), name = VALUES(name)";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getIconId());
            preparedStatement.setString(4, user.getName());

            int i = preparedStatement.executeUpdate();

            if(i > 0) {

                Log.i("MysqlDatabase", "insertDoll", "データの挿入は成功しました");
                isInsertend = true;

            }else {

                Log.i("MysqlDatabase", "insertDoll", "データの挿入に失敗しました");

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

            return isInsertend;


    }

    public boolean insertDoll (Doll doll, String userId) {

        boolean isInsertend = false;

        String sqlCommand = "INSERT INTO " + TABLE_NAME_DOLL + " (userid, name, exp, level, frameid, backgroundid, image) VALUES (?, ?, ?, ?, ?, ?, ?)" +
                "ON DUPLICATE KEY UPDATE userid = userid, name = VALUES(name), exp = VALUES(exp), level = VALUES(level), frameid = VALUES(frameid), backgroundid = VALUES(backgroundid), image + VALUES(image)";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, doll.getName());
            Log.i("MysqlDatabase", "insertDoll", "ぬいぐるみデータを保存中");
            preparedStatement.setInt(3, doll.getExp());
            preparedStatement.setInt(4, doll.getLevel());
            preparedStatement.setInt(5, doll.getFrameId());
            preparedStatement.setInt(6, doll.getBackgroundId());
            preparedStatement.setString(7, doll.getImage());

            int i = preparedStatement.executeUpdate();



            if(i > 0) {

                isInsertend = true;
                Log.i("MysqlDatabase", "insertDoll", "データの挿入は成功しました");

            }else {

                Log.i("MysqlDatabase", "insertDoll", "データの挿入に失敗しました");

            }

        } catch (SQLException e) {

            Log.i("MysqlDatabase", "insertDoll", "insert error");

        }

        return isInsertend;

    }

    public Doll selectDoll(String userId) {

        Doll doll = null;

        try {

            if(statement == null) {

                Log.i("MysqlDatabase", "select", "statement is null object");
                return null;

            }else {

                String sqlCommand = "SELECT name, exp, level, frameid, backgroundid, image FROM " + TABLE_NAME_DOLL + " WHERE user_id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
                preparedStatement.setString(1, userId);
                rs = preparedStatement.executeQuery();

                while (rs.next()) {

                    doll = new Doll();
                    doll.setName(rs.getString("name"));
                    doll.setExp(rs.getInt("exp"));
                    doll.setLevel(rs.getInt("level"));
                    doll.setFrameId(rs.getInt("frameid"));
                    doll.setBackgroundId(rs.getInt("backgroundid"));
                    doll.setImage(rs.getString("image"));

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }

        return doll;

    }


    public void close() {

        Log.i("MysqlDatabase", "cloes", "データベースを閉じてる");

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

            Log.i("MysqlDatabase", "cloes", "データベースを閉じました");

        } catch (SQLException e) {

            Log.i("MysqlDatabase", "close", "close error");
            e.printStackTrace();

        }

    }


}

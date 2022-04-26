package Main.Model.DB;

import java.sql.Connection;
import java.sql.SQLException;

public class MysqlConnection extends ConnectionDB {
    private String port = "3306";
    private String database = "banque";
    private static Connection connection;

    public MysqlConnection() {
        super();
        String driver = "com.mysql.jdbc.Driver";
        this.setDriver(driver);
        this.setUser("root");
        this.setUrl(this.UrlConnection());
    }

    public Connection getConnection() {
        if (MysqlConnection.this.connection == null){
            try {
                MysqlConnection.this.connection = this.connection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return MysqlConnection.this.connection;
    }


    private String UrlConnection(){
        //String URL = "jdbc:mysql://localhost:3306/database";
        String url = "jdbc:mysql://";

        return url + this.getHost() + ":" + this.getPort() +"/"+ this.getDatabase();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String getDatabase() {
        return database;
    }

    @Override
    public void setDatabase(String database) {
        this.database = database;
    }
}

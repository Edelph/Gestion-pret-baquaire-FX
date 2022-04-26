package Main.Model.DB;

import Main.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionDB {


    protected String password="";
    protected String user ;
    protected String url;
    protected String Driver;
    protected String host = "localhost";

    protected String database;


    public ConnectionDB(String url,String user,String password){
        this.password = password;
        this.user = user;
        this.url = url;
    }
    public  ConnectionDB(String url,String user){
        this.password = "";
        this.user = user;
        this.url = url;
    }
    public ConnectionDB(String Driver){
        this.Driver = Driver;
    }

    public ConnectionDB() {

    }

    public Connection connection() throws ClassNotFoundException, SQLException {

        Class.forName(this.Driver);
        Connection connection = DriverManager.getConnection(this.url,this.user,this.password);

        return connection;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
    public  String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}

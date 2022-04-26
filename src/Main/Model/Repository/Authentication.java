package Main.Model.Repository;

import Main.Model.DB.MysqlConnection;
import Main.Model.Entity.User;

import java.sql.*;

public class Authentication {
    private static Connection connection;
    private String table = "user";
    private static User user;

    public Authentication() {
        if(Authentication.this.connection == null){
            this.connection = (new MysqlConnection()).getConnection();
        }
    }
    public User getUser() {
        return Authentication.this.user;
    }

    public User signing(User user) throws SQLException {
        String querry = "SELECT * FROM "+ this.table + " WHERE name=? AND password=? ";

        PreparedStatement preparedStatement = this.connection.prepareStatement(querry);

        preparedStatement.setObject(1, user.getName());
        preparedStatement.setObject(2, user.getPassword());

        ResultSet Rs = preparedStatement.executeQuery();

        return this.getUser(Rs);
    }
    public boolean updateUser(User user) {
        String querry = "UPDATE "+ this.table + " SET name=?, password=? WHERE name=? AND password=?";


        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(querry);
            preparedStatement.setObject(1, user.getName());
            preparedStatement.setObject(2, user.getNewPassword());
            preparedStatement.setObject(3, this.user.getName());
            preparedStatement.setObject(4, this.user.getPassword());

            if (preparedStatement.executeUpdate()>0){
                Authentication.this.user = user;
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
         user = new User();
        if (resultSet.next()){
            try{
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return user;
    }



}

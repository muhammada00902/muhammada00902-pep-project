package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {


    public Account geAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account a where a.username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("account_id");
                String password = rs.getString("password");

                return new Account(id, username, password);
            }
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql =  "SELECT * FROM account a where a.account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                return new Account();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    

    

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account(name, password) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_author_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_author_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}

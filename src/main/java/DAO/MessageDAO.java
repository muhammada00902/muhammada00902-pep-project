package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Message;
import Util.ConnectionUtil;

import java.util.ArrayList;
import java.util.List;


public class MessageDAO {
    
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                int postedById = message.getPosted_by();
                String text =  message.getMessage_text();
                Long time = message.getTime_posted_epoch();
                return new Message(generated_message_id, postedById, text, time);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                
                int id = rs.getInt("message_id");
                int postedby = rs.getInt("posted_by");
                String text = rs.getString("message_text");
                Long time = rs.getLong("time_posted_epoch");
               
                Message message = new Message(id, postedby, text, time);
                
                messages.add(message);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById (int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql =  "SELECT * FROM message m where m.message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {

                int postedby = rs.getInt("posted_by");
                String text = rs.getString("message_text");
                Long time = rs.getLong("time_posted_epoch");
               
                return new Message(id, postedby, text, time);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql =  "DELETE FROM message m where m.message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {

                int postedby = rs.getInt("posted_by");
                String text = rs.getString("message_text");
                Long time = rs.getLong("time_posted_epoch");
               
                return new Message(id, postedby, text, time);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessageById(int id, String newText) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql =  "UPDATE message m SET message_text = ? where m.message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, newText);
            preparedStatement.setInt(2, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {

                int postedby = rs.getInt("posted_by");
                String text = rs.getString("message_text");
                Long time = rs.getLong("time_posted_epoch");
               
                return new Message(id, postedby, text, time);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessagesOfUser(int userId){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * from message where posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                
                int id = rs.getInt("message_id");
                int postedby = rs.getInt("posted_by");
                String text = rs.getString("message_text");
                Long time = rs.getLong("time_posted_epoch");
               
                Message message = new Message(id, postedby, text, time);
                
                messages.add(message);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }


}


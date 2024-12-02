package Service;

import DAO.MessageDAO;
import Model.Message;

import Model.Account;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountService accountService;

    public MessageService() {
        messageDAO = new MessageDAO();
        this.accountService = new AccountService();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
        this.accountService = new AccountService();
    }


    // Main Methods / CRUD
    public Message addMessage(Message message) {
        if (!isValidMessage(message)) {
            return null;
        }

        return messageDAO.insertMessage(message);

    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessage(int id) {
        Message msg = getMessage(id);
        messageDAO.deleteMessageById(id);
        return msg;
    }

    public Message updateMessage(int id, Message messageWithText) {
        String newMessageText = messageWithText.getMessage_text(); 
                                                                        
        if (newMessageText == null || newMessageText.length() > 255 || newMessageText.length() == 0) {
            return null;
        }

        if (getMessage(id) == null) {
            return null;
        }

        int rowsAffectedAfterUpdate = messageDAO.updateMessageById(id, newMessageText);
        if (rowsAffectedAfterUpdate == 1) {
            return getMessage(id);
        }

        return null;

    }

    public List<Message> getAllMessagesOfUser(int userId) {
        return messageDAO.getAllMessagesOfUser(userId);
    }


    // Helpers
    public boolean isValidMessage(Message message) {
        // message_text is not blank and under 255 chars
        String messageText = message.getMessage_text();
        if (messageText == null || messageText.length() == 0 || messageText.length() >= 255) {
            return false;
        }
        
        // posted_by refers to a real, existing user
        int userId = message.getPosted_by();
        Account userAccount = accountService.getAccount(userId);

        return (userAccount != null);
        
    }
}
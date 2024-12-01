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
        return messageDAO.deleteMessageById(id);
    }

    public Message updateMessage(int id, Message messageWithText) {
        String newMessageText = messageWithText.getMessage_text();

        if (newMessageText == null || newMessageText.length() > 255) {
            return null;
        }

        if (getMessage(id) == null) {
            return null;
        }

        return messageDAO.updateMessageById(id, newMessageText);

    }

    public List<Message> getAllMessagesOfUser(int userId) {
        return messageDAO.getAllMessagesOfUser(userId);
    }


    // Helpers
    public boolean isValidMessage(Message message) {
        // message_text is not blank and under 255 chars
        String messageText = message.getMessage_text();
        if (messageText == null || messageText.length() >= 255) {
            return false;
        }
        
        // posted_by refers to a real, existing user
        int userId = message.getPosted_by();
        Account userAccount = accountService.getAccount(userId);

        return (userAccount != null);
        
    }
}
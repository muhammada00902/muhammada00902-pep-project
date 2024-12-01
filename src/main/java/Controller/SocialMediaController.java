package Controller;

// to add my own imports 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Message;
import Service.MessageService;

import java.util.List;



/**
 * TODOx: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginUserHandler);
        app.post("/messages", this::newMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messagess/{message_id}", this::getMessageByIdHander);
        app.delete("/messagess/{message_id}", this::deleteMessageByIdHander);
        app.patch("/messagess/{message_id}", this::updateMessageByIdHander);
        app.get("/accounts/{account_id}/messages", this::getMessagesOfUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


    // MY NEW ONES TO COMPLETE TODOS
    private void registerUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.addAccount(account);

        if (newAccount != null) {
            ctx.json(mapper.writeValueAsString(newAccount));
        } else {
            ctx.status(400);
        }

    }

    private void loginUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account currAccount = accountService.tryLogin(account);

        if (currAccount != null) {
            ctx.json(mapper.writeValueAsString(currAccount));
        } else{ 
            ctx.status(401);
        }
    }

    private void newMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.addMessage(message);

        if (newMessage != null) {
            ctx.json(mapper.writeValueAsString(newMessage));
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByIdHander(Context ctx) throws JsonProcessingException {
        String messageIdString = ctx.pathParam("message_id");
        int messageId = Integer.parseInt(messageIdString);

        Message message = messageService.getMessage(messageId);
        if (message != null) {
            ObjectMapper mapper = new ObjectMapper();
            ctx.json(mapper.writeValueAsString(message));
        } else {
            ctx.status(200);
        }

    }

    private void deleteMessageByIdHander(Context ctx) throws JsonProcessingException {
        String messageIdString = ctx.pathParam("message_id");
        int messageId = Integer.parseInt(messageIdString);

        Message deletedMessage = messageService.deleteMessage(messageId);
        if (deletedMessage != null) {
            ObjectMapper mapper = new ObjectMapper();
            ctx.json(mapper.writeValueAsString(deletedMessage));
        } else {
            ctx.status(200);
        }

    }

    private void updateMessageByIdHander(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message messageWithText = mapper.readValue(ctx.body(), Message.class);

        String messageIdString = ctx.pathParam("message_id");
        int messageId = Integer.parseInt(messageIdString);

        Message updatedMessage = messageService.updateMessage(messageId, messageWithText);
        if (updatedMessage != null) {
            ctx.json(mapper.writeValueAsString(updatedMessage));
        } else {
            ctx.status(400);
        }
    }

    private void getMessagesOfUser(Context ctx) throws JsonProcessingException {
        String userIdString = ctx.pathParam("account_id");
        int userId = Integer.parseInt(userIdString);

        List<Message> messages = messageService.getAllMessagesOfUser(userId); 
        ctx.json(messages);    
    }


}
package com.example.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    // CONSTRUCTORS //
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }



    // ACCOUNT HANDLERS //
    /** 
	 * Handler for registering a new account. 
	 *  
	 * The new account's username must not be be blank, nor have a password 
	 * less than 4 characters long. Failing to meet these requirements will 
	 * cancel the POST and the API will return a 400 message (client error).
	 * 
	 * The new account's username must also not be the same as another already
	 * existing account's. Failing to meet this requirement will cancel the
	 * POST and the API will return a 409 message (conflict error).
	 *  
	 * If AccountService returns a null account (meaning posting an Account was 
	 * unsuccessful), the API will also return a 400 message (client error).
	 *  
	 * @param account	the new account trying to be registered
	 * 
	 * @return			the new register account, or "null" if registration
	 * 					failed
	 */
    @PostMapping(value = "/register")
	public @ResponseBody ResponseEntity<Account> postRegisterAccountHandler(@RequestBody Account account) { 
		return this.accountService.registerAccount(account);
	}

	/** 
	 * Handler to authorize/log in accounts. 
	 *  
	 * If AccountService returns a null account (meaning login was 
	 * unsuccessful, such as due to an invalid username or password), the API 
	 * will return a 401 message (unauthorized error). 
	 *  
	 * @param account	the account credintials being used to log in
	 *
	 * @return			the logged in account, or "null" if
	 * 					login/authorization failed
	 */
	@PostMapping(value = "/login")
	public @ResponseBody ResponseEntity<Account> postLoginAccountHandler(@RequestBody Account account) { 
		return this.accountService.loginAccount(account);
	} 
 
 
 
	// MESSAGE HANDLERS // 
	/** 
	 * Handler for posting a new message. 
	 *  
	 * The text content of the message can't be blank, nor exceed 253 
	 * characters in length (though the column of the database has a size of 
	 * 255 bytes, the last 2 bytes are reserved for storing size). Failing to 
	 * meet these requirements will cancel the POST and the API will return a 
	 * 400 message (client error).
	 * 
	 * The person posting the message must also be a user existing in the
	 * database. Failing to meet this requirement will cancel the POST and the
	 * API will return a 400 message (client error).
	 *  
	 * If MessageService returns a null message (meaning posting an Message was 
	 * unsuccessful), the API will return a 400 message (client error). 
	 *  
	 * @param message	the message to be created 
	 * 
	 * @return			the newly created message, or "null" if message
	 * 					creation failed
	 */
	@PostMapping(value = "/messages")
	public @ResponseBody ResponseEntity<Message> postCreateMessageHandler(@RequestBody Message message) { 
		return this.messageService.createMessage(message);
	}

	/** 
	 * Handler to retrieve all messages.
	 * 
	 * Response message should always be 200 (OK), even if no messages are
	 * returned.
	 *  
	 * @return	list of all messages
	 */
	@GetMapping(value = "/messages")
	public @ResponseBody ResponseEntity<List<Message>> getAllMessagesHandler() { 
		return this.messageService.getAllMessages();
	} 
 
	/** 
	 * Handler to retrieve all messages by a single account.
	 * 
	 * Response message should always be 200 (OK), even if no messages by
	 * specified account are returned.
	 * 
	 * @param accountId	ID of the account to retrieve all messages of
	 *  
	 * @return	list of all messages by the specified account
	 */
	@GetMapping(value = "/accounts/{accountId}/messages")
	public @ResponseBody ResponseEntity<List<Message>> AllMessagesByAccountHandler(@PathVariable int accountId) { 
		return this.messageService.getAllMessagesByAccount(accountId);
	} 
 
	/** 
	 * Handler to recieve a specific message by its ID.
	 * 
	 * Response message should always be 200 (OK), even if the message with the
	 * specified ID doesn't exist (and thus isn't returned).
	 * 
	 * @param messageId	ID of the message to retrieve
	 *  
	 * @return	message with the specified ID
	 */
	@GetMapping(value = "/messages/{messageId}")
	public @ResponseBody ResponseEntity<Message> getMessageByIdHandler(@PathVariable int messageId) { 
		return this.messageService.getMessage(messageId);
	}

	/** 
	 * Handler for updating the body text of a pre-existing message. 
	 *  
	 * The message's updated body text must not be be blank, nor exceed 253 
	 * characters in length (though the column of the database has a size of 
	 * 255 bytes, the last 2 bytes are reserved for storing size). Failing to 
	 * meet these requirements will cancel the PATCH and the API will return a 
	 * 400 message (client error). 
	 *  
	 * @param messageId	ID of the message to update
	 * 
	 * @return	the number of rows updated
	 */
	@PatchMapping(value = "/messages/{messageId}")
	public @ResponseBody ResponseEntity<String> patchUpdateMessageHandler(@PathVariable int messageId, @RequestBody Message message) { 
		return this.messageService.updateMessageText(messageId, message);
	} 
 
	/** 
	 * Handler for deleting a message. 
	 *  
	 * Response message should always be 200 (OK), even if the message with the
	 * specified ID doesn't exist (and thus isn't deleted).
	 *  
	 * @param messageId	ID of the message to delete
	 * 
	 * @return	the number of rows deleted
	 */
	@DeleteMapping(value = "/messages/{messageId}")
	public @ResponseBody ResponseEntity<String> deleteMessageHandler(@PathVariable int messageId) { 
		return this.messageService.deleteMessageById(messageId);
	} 

}

package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

/** 
 * This is a Service class that acts between the endpoints (controller) and the 
 * database (repository) of the "Message" Java class, validating input. 
 */
@Service
public class MessageService {
    MessageRepository messageRepository;
	AccountRepository accountRepository;

    // CONSTRUCTORS //
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
		this.accountRepository = accountRepository;
    }

    // CREATE OPERATIONS //
	/** 
	 * This registers a new message into the "message" database table. 
	 *  
	 * @param message   The new message to be created 
	 *  
	 * @return	Message if it was successfully persisted; or "null" if it 
	 *		    wasn't successfully persisted or if new message text is blank 
	 *		    or too long, or poster isn't in database
	 */ 
	public ResponseEntity<Message> createMessage(Message message) { 
		// Check message text isn't blank 
		if(message.getMessageText() == "") { 
			return ResponseEntity.status(400).body(null); 
		} 
		// Check message text isn't more than 253 characters 
		if(message.getMessageText().length() > 253) { 
			return ResponseEntity.status(400).body(null); 
		}

		// Check if message poster is a user in the database
		if(this.accountRepository.findByAccountId(message.getPostedBy()) == null) {
			return ResponseEntity.status(400).body(null);
		}
 

        Message addedMessage = this.messageRepository.save(message);

        if (addedMessage != null) { // Message successfully created
            return ResponseEntity.status(200).body(addedMessage);

        } else { // Creation failed due to other error
            return ResponseEntity.status(400).body(null); 
        }
	} 
 
	// READ OPERATIONS // 
	/** 
	 * Gets all messages. 
	 *  
	 * @return  All messages
	 */ 
	public ResponseEntity<List<Message>> getAllMessages() { 
        List<Message> messages = this.messageRepository.findAll();

        return ResponseEntity.status(200).body(messages);
	} 
 
	/** 
	 * Gets all messages by one account. 
	 *  
	 * @param account_id    ID of the account that we want all messages from 
	 * @return  All messages by one account 
	 */ 
	public ResponseEntity<List<Message>> getAllMessagesByAccount(int accountId) {
        List<Message> messages = this.messageRepository.findAllByPostedBy(accountId);

		return ResponseEntity.status(200).body(messages); 
	} 
 
	/** 
	 * Gets message with the corresponding ID. 
	 *  
	 * @param message_id    ID of the message that we want 
	 * @return	Singular message with the specified ID, or "null" if it wasn't 
	 *		successfully retrieved 
	 */ 
	public ResponseEntity<Message> getMessage(int messageId) {
        Message message = this.messageRepository.findByMessageId(messageId);

		return ResponseEntity.status(200).body(message); 
	} 

}

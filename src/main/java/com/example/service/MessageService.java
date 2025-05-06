package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
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
	@Transactional(readOnly = true)
	public ResponseEntity<List<Message>> getAllMessages() { 
        List<Message> messages = this.messageRepository.findAll();

        return ResponseEntity.status(200).body(messages);
	} 
 
	/** 
	 * Gets all messages by one account. 
	 *  
	 * @param account_id    ID of the account that we want all messages from
	 * 
	 * @return  All messages by one account 
	 */
	@Transactional(readOnly = true)
	public ResponseEntity<List<Message>> getAllMessagesByAccount(int accountId) {
        List<Message> messages = this.messageRepository.findAllByPostedBy(accountId);

		return ResponseEntity.status(200).body(messages); 
	} 
 
	/** 
	 * Gets message with the corresponding ID. 
	 *  
	 * @param message_id    ID of the message that we want
	 * 
	 * @return	Singular message with the specified ID, or "null" if it wasn't 
	 *			successfully retrieved 
	 */
	@Transactional(readOnly = true)
	public ResponseEntity<Message> getMessage(int messageId) {
        Message message = this.messageRepository.findByMessageId(messageId);

		return ResponseEntity.status(200).body(message); 
	} 

	// UPDATE OPERATIONS // 
	/** 
	 * Updates the text of a message. 
	 *  
	 * @param message_id    ID of the message we want to update
	 * @param message	Message object with new text to replace the old 
	 *					message text with 
	 *  
	 * @return	count of messages updated (which should be 1), or "null" if it 
	 *			wasn't successfully updated or if updated messaged text is
	 *			blank or too long 
	 */
	@Transactional
	public ResponseEntity<String> updateMessageText(int messageId, Message message) { 
		// Check new message text isn't blank 
		if(message.getMessageText() == "") { 
			return ResponseEntity.status(400).body(null); 
		} 
		// Check new message text isn't more than 253 characters 
		if(message.getMessageText().length() > 253) { 
			return ResponseEntity.status(400).body(null);
		}


		Message messageToUpdate = this.messageRepository.findByMessageId(messageId);

		if (messageToUpdate != null) { // Message already exists
			messageToUpdate.setMessageText(message.getMessageText());
			Message updatedMessage = this.messageRepository.save(messageToUpdate);

			if (updatedMessage != null) { // Message successfully updated
				return ResponseEntity.status(200).body("1");
			}
		}

		// Pre-existing message not found or updated
        return ResponseEntity.status(400).body(null); 
	} 
 
	// DELETE OPERATIONS 
	/** 
	 * Deletes message with the corresponding ID.
	 *  
	 * @param message_id    ID of the message that we want to delete
	 * 
	 * @return	count of messages deleted (shoulde be 1), or an empty string if
	 * 			deletion wasn't successful
	 */
	@Transactional()
	public ResponseEntity<String> deleteMessageById(int messageId) { 
		int deletedMessageCount = this.messageRepository.deleteByMessageId(messageId);

		if (deletedMessageCount != 0) { // Message successfully deleted
            return ResponseEntity.status(200).body(String.valueOf(deletedMessageCount));

        } else { // Deletion not fulfilled
            return ResponseEntity.status(200).body(""); 
        }
	} 
}

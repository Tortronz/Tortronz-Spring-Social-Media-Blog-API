package com.example.repository;

import com.example.entity.Account;
import com.example.entity.Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 
 * This is a data access repository class that manages the interactions Java
 * objects of the class "Message" and the SQL database table "message".
 *  
 * The database table "message" has the columns: 
 * message_id		    int		        Primary key, and unique 
 * 						                identifiers for messages 
 * posted_by		    int		        Foreign key to "account" table, ID 
 *						                of account who posted message 
 * message_text         varchar(255)    Text of message 
 * time_posted_epoch	bigint		    Time message was posted 
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    /**
     * Returns the message with the specified ID.
     * @param messageId
     * @return  message with the specified ID
     */
    Message findByMessageId(int messageId);

    /**
     * Returns a list of messages by the specified account.
     * @param postedBy
     * @return  list of messages by the specified account
     */
    List<Message> findAllByPostedBy(int postedBy);

    /**
     * Deletes the message with the specified ID.
     * @param messageId
     * @return  deleted message with the specified ID
     */
    int deleteByMessageId(int messageId);
}

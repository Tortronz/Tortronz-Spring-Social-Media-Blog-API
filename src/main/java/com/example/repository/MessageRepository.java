package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface MessageRepository extends JpaRepository<Message, Long> {
}

package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;

/** 
 * This is a Service class that acts between the endpoints (controller) and the 
 * database (repository) of the "Message" Java class, validating input. 
 */
@Service
public class MessageService {
    MessageRepository messageRepository;

    // CONSTRUCTORS //
    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // CREATE OPERATIONS //
}

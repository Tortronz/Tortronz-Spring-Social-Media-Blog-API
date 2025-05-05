package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;

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
	 * If AccountService returns a null account (meaning posting an Account was 
	 * unsuccessful), the API will return a 400 message (client error). 
	 *  
	 * @param ctx		data handler for HTTP requests and responses, provided the 
	 *                  Javalin app 
	 * @throws JsonProcessingException	if there's an issue converting JSON 
	 *                                  into an object 
	 */
    @PostMapping(value = "/register")
	public @ResponseBody Account postRegisterAccountHandler(@RequestBody Account account) { 
		return this.accountService.registerAccount(account);
	}

}

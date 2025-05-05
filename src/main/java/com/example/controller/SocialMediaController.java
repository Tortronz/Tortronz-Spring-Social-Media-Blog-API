package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
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

}
